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
                clientListenThread = new Thread(SocketListenerThreadStart);
                clientListenThread.Start();
            }
        }


        private void SocketListenerThreadStart()
        {

            int size = 0;
            Byte[] byteBuffer = new Byte[1024];



            Timer t1 = new Timer(new TimerCallback(CheckClientCommInterval), requestSocket, 20000, 0);
            Timer t2 = new Timer(new TimerCallback(CheckClientCommInterval), requestSocket, 40000, 0);
            Timer t3 = new Timer(new TimerCallback(CheckClientCommInterval), requestSocket, 60000, 0);

            while (!m_stopClient)
            {
                try
                {
                  lock (this)
                   { 

                    size = requestSocket.Receive(byteBuffer);
                    if (size > 0)
                    {
                        m_currentReceiveDateTime = DateTime.Now;

                        ParseReceiveBuffer(byteBuffer, size);

                        t1.Change(20000, 0);
                        t2.Change(40000, 0);
                        t3.Change(60000, 0);
                        m_lastReceiveDateTime = DateTime.Now;


                        m_stopClient = false;
                        System.Diagnostics.Debug.WriteLine("\tTime:" + m_lastReceiveDateTime);
                    }
                      else
                    {
                        m_stopClient = true;
                    }
                    }
                   
                }
                catch (SocketException se)
                {
                    m_stopClient = true;
                    m_markedForDeletion = true;

                }
            }
            //t.Change(Timeout.Infinite, Timeout.Infinite);
            //t = null;
           
        }

        private void ParseReceiveBuffer(byte[] byteBuffer, int size)
        {
            string alive = System.Text.Encoding.ASCII.GetString(byteBuffer);
            System.Diagnostics.Debug.WriteLine(alive + "\n");
        }


        private void CheckClientCommInterval(object state)
        {
            clientListenThread.Abort();
            if (!(DateTime.Compare(m_lastReceiveDateTime,new DateTime(0001,01,01)) == 0))
            {
                    DateTime currentTime = DateTime.Now;
                    System.Diagnostics.Debug.WriteLine("\tTime:" + m_lastReceiveDateTime + "\t Received Interrupt at:" + currentTime);

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
                        try
                        {
                            requestSocket.Close();
                            clientListenThread.Abort();
                            WebSiteControl rollover = new WebSiteControl();
                            rollover.StopWebsite("architecture12");
                            rollover.StartWebsite("architecture11");
                            
                        }
                        catch (Exception)
                        {
                            WebSiteControl rollover = new WebSiteControl();
                            rollover.StopWebsite("architecture12");
                            rollover.StartWebsite("architecture11");
                            throw;
                        }
                       
                    }
                    
            }
              


        }



        public bool m_markedForDeletion { get; set; }

        public bool m_stopClient { get; set; }

        public DateTime m_lastReceiveDateTime { get; set; }

        public DateTime m_currentReceiveDateTime { get; set; }
    }
}
