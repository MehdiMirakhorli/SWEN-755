using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace Bnk.ClientApplication
{
    class TCPClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        Thread t;
        private String m_fileName = null;
        public TCPClient(String fileName)
        {
            m_fileName = fileName;
            t = new Thread(new ThreadStart(ClientThreadStart));
            t.Start();
        }

        private void ClientThreadStart()
        {
            Socket clientSocket = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream, ProtocolType.Tcp);
            clientSocket.Connect(new IPEndPoint(IPAddress.Parse("127.0.0.1"), 31001));

            while (true)
            {
                Random random = new Random();
                int value = random.Next(1, 10);

                System.Diagnostics.Debug.WriteLine("Random Value:" + value);
                //Random random = new Random();
                //int value = random.Next(1, 10);
                if (value > 5)
                {
                    clientSocket.Close();
                    System.Diagnostics.Debug.WriteLine("CONNECTION CLOSED");
                    break;
                }
                else
                {
                    try
                    {
                        System.Threading.Thread.Sleep(19000);
                        clientSocket.Send(Encoding.ASCII.GetBytes(m_fileName));
                        System.Diagnostics.Debug.WriteLine("ALIVE");
                    }
                    catch (Exception ex)
                    {

                        System.Diagnostics.Debug.WriteLine("THREAD ABORTED");
                        t.Abort();
                        throw ex;
                    }
                }
            }
        }
    }
}
