using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Bnk.Log.Monitor.Areas.HeartBeat
{
    /// <summary>
    /// HeartBeat Receiver which receives the message from the Payment Reader
    /// </summary>
    class HeartBeatReceiver
    {
        #region Constant
        public const int CRITICAL_TIMEOUT = 60;
        public const int MAJOR_TIMEOUT = 40;
        public const int WARNING_TIMEOUT = 20;
        #endregion

        #region Private Global Variables
        
        public bool _hbStop { get; set; }
        Thread m_purgingThread;
        Thread _receiveHbThread;
        TcpListener _hbreceive;
        ArrayList m_socketListenersList;
        const int PORT = 31001;
        string address = "127.0.0.1";
        public bool m_stopServer;
        public bool m_stopPurging;
        #endregion

        #region Constructor
        public HeartBeatReceiver()
        {
            try
            {
                int intAddress = BitConverter.ToInt32(IPAddress.Parse(address).GetAddressBytes(), 0);
                IPAddress myip = Dns.GetHostEntry("localhost").AddressList[0];
                _hbreceive = new TcpListener(new IPEndPoint(IPAddress.Parse("127.0.0.1"), PORT));
            }
            catch (Exception ex)
            {
                System.Diagnostics.EventLog.WriteEntry("Please", ex.InnerException.ToString());
                _hbreceive = null;               
            }
        }
        #endregion

        #region Start the Heart Beat Service on separate thread
        public void StartServer()
        {
            System.Diagnostics.EventLog.WriteEntry("HeartBeat Monitor", "Server Started");

            if (_hbreceive != null)
            {
                // Create a ArrayList for storing SocketListeners before
                // starting the server.
                m_socketListenersList = new ArrayList();

                // Start the Server and start the thread to listen client 
                // requests.
               

                _hbreceive.Start();
                _receiveHbThread = new Thread(new ThreadStart(ServerThreadStart));
                System.Diagnostics.EventLog.WriteEntry("HeartBeat Monitor", "Listening on port" + PORT.ToString());

                _receiveHbThread.Start();

                m_purgingThread = new Thread(new ThreadStart(PurgingThreadStart));
                m_purgingThread.Priority = ThreadPriority.Lowest;
                m_purgingThread.Start();

            }
        }
        #endregion

        #region
        public void PurgingThreadStart()
        {
            foreach (HeartBeatSocketListener item in m_socketListenersList)
            {
                   if (item.m_markedForDeletion)
                   {
                       item.requestSocket.Close();
                   }
            }
        }
        #endregion


        #region Server Thread Start
        private void ServerThreadStart()
        {
           

            Socket ServerSocket = null;
            HeartBeatSocketListener socketListener = null;
            try
            {
                while (!_hbStop)
                {
                    System.Diagnostics.EventLog.WriteEntry("Server Thread Started","Monitor");

                    ServerSocket = _hbreceive.AcceptSocket();

                    socketListener = new HeartBeatSocketListener(ServerSocket);
                   

                    lock (m_socketListenersList)
                    {
                        m_socketListenersList.Add(socketListener);
                    }

                    socketListener.StartSocketListener();
                }
            }
            catch (Exception ex)
            {
                _hbStop = true;
            }
        }
        #endregion

        public void StopServer()
        {
            if (_hbreceive != null)
            {
                System.Diagnostics.EventLog.WriteEntry("HeartBeat Monitor", "Purging all threads and clossing all connections");
                // It is important to Stop the server first before doing
                // any cleanup. If not so, clients might being added as
                // server is running, but supporting data structures
                // (such as m_socketListenersList) are cleared. This might
                // cause exceptions.

                // Stop the TCP/IP Server.
                m_stopServer = true;
                _hbreceive.Stop();

                // Wait for one second for the the thread to stop.
                _receiveHbThread.Join(1000);

                // If still alive; Get rid of the thread.
                if (_receiveHbThread.IsAlive)
                {
                    _receiveHbThread.Abort();
                }
                _receiveHbThread = null;

                m_stopPurging = true;
                m_purgingThread.Join(1000);
                if (m_purgingThread.IsAlive)
                {
                    m_purgingThread.Abort();
                }
                m_purgingThread = null;

                // Free Server Object.
                _hbreceive = null;

                // Stop All clients.
                StopAllSocketListers();
            }
        }

        public void StopAllSocketListers()
        {
            foreach (HeartBeatSocketListener item in m_socketListenersList)
            {
                item.requestSocket.Close();
            }
        }
    }
}
