using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Bnk.Log.Monitor.Areas.FaultPrevention;

namespace Bnk.Log.Monitor.Areas.HeartBeat
{
    class HeartBeatSocketListener
    {
        public Socket requestSocket;
        Thread clientListenThread;
        bool _received = false;
        Dictionary<Thread, DateTime> lastreceivedict = new Dictionary<Thread, DateTime>();
        public HeartBeatSocketListener(Socket clientSocket)
        {
            requestSocket = clientSocket;
        }

        public void StartSocketListener()
        {
            if (requestSocket != null)
            {
                SocketListenerThreadStart();
                //clientListenThread =
                //  new Thread(new ThreadStart(SocketListenerThreadStart));

                //clientListenThread.Start();
                //SocketListenerThreadStart();
            }
        }

       
        private void SocketListenerThreadStart()
        {

            int size = 0;
            Byte[] byteBuffer = new Byte[1024];

          
            Timer t = new Timer(new TimerCallback(CheckClientCommInterval),this, 20000, 20000);
            


            while (!m_stopClient)
            {
                try
                {
                    size = requestSocket.Receive(byteBuffer);
                    m_currentReceiveDateTime = DateTime.Now;
                    ParseReceiveBuffer(byteBuffer, size);
                    lock (this)
                    {
                        m_lastReceiveDateTime = DateTime.Now;
                        
                        t.Change(20000, 20000);
                        m_stopClient = false;
                        System.Diagnostics.Debug.WriteLine("\tParsed Code Time:" + m_lastReceiveDateTime);
                    }

                }
                catch (SocketException se)
                {
                    m_stopClient = true;
                    m_markedForDeletion = true;
                }
            }
            t.Change(Timeout.Infinite, Timeout.Infinite);
            t = null;
        }

        private void ParseReceiveBuffer(byte[] byteBuffer, int size)
        {
            string alive = System.Text.Encoding.ASCII.GetString(byteBuffer);
            System.Diagnostics.Debug.WriteLine(alive + "\n");
        }


        private void CheckClientCommInterval(object state)
        {
                
                DateTime currentTime = DateTime.Now;
                System.Diagnostics.Debug.WriteLine("\tReached Event Time:" + m_lastReceiveDateTime + "\t Received Interrupt at:" + currentTime);

                if ((currentTime.Subtract(m_lastReceiveDateTime).TotalSeconds >= 20) && (currentTime.Subtract(m_lastReceiveDateTime).TotalSeconds < 40))
                {
                    System.Diagnostics.Debug.WriteLine("Client Communication Interval exceeded\n");
                }
                else if (((currentTime.Subtract(m_lastReceiveDateTime).TotalSeconds >= 40) && (currentTime.Subtract(m_lastReceiveDateTime).TotalSeconds < 60)))
                {
                    System.Diagnostics.Debug.WriteLine("Warning\n");
                }
                else if (((currentTime.Subtract(m_lastReceiveDateTime).TotalSeconds >= 60)))
                {
                    System.Diagnostics.Debug.WriteLine("Critical\n");
                    m_stopClient = true;
                    WebSiteControl rollover = new WebSiteControl();
                    rollover.StopWebsite("architecture12");
                    rollover.StartWebsite("architecture11");
                }
            

        }



        public bool m_markedForDeletion { get; set; }

        public bool m_stopClient { get; set; }

        public DateTime m_lastReceiveDateTime { get; set; }

        public DateTime m_currentReceiveDateTime { get; set; }
    }
}
