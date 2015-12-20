using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace WebSite19
{
    class TCPClient
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>

        private String m_fileName = null;
        Thread t;
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

            //Random random = new Random();
            //int value = random.Next(1, 4);
            //System.Diagnostics.Debug.WriteLine("VALUE" + value);
            //if (value > 3)
            //{
            //clientSocket.Close();
            //System.Diagnostics.Debug.WriteLine("CONNECTION CLOSED");
            //}
            // Send the file name.
            clientSocket.Send(Encoding.ASCII.GetBytes(m_fileName));
            while (true)
            {
                Random random = new Random();
                int value = random.Next(1, 10);

                System.Diagnostics.Debug.WriteLine("Random Value:"+value);
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
            // Receive the length of the filename.
            //byte[] data = new byte[128];
            //clientSocket.Receive(data);
            //int length = BitConverter.ToInt32(data, 0);

            //clientSocket.Send(Encoding.ASCII.GetBytes(m_fileName + ":" +
            //    "this is a test\r\n"));

            //clientSocket.Send(Encoding.ASCII.GetBytes(m_fileName + ":" +
            //      "THIS IS "));
            //clientSocket.Send(Encoding.ASCII.GetBytes("ANOTHRER "));
            //clientSocket.Send(Encoding.ASCII.GetBytes("TEST."));
            //clientSocket.Send(Encoding.ASCII.GetBytes("\r\n"));
            //clientSocket.Send(Encoding.ASCII.GetBytes(m_fileName + ":" +
            //      "TEST.\r\n" + m_fileName + ":" + "TEST AGAIN.\r\n"));
            //clientSocket.Send(Encoding.ASCII.GetBytes("[EOF]\r\n"));

            // Get the total length
            //clientSocket.Receive(data);
            //length = BitConverter.ToInt32(data, 0);

        }
    }
}