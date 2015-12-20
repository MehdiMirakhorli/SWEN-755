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
        
        private String m_fileName = null;
        public TCPClient(String fileName)
        {
            m_fileName = fileName;
            Thread t = new Thread(new ThreadStart(ClientThreadStart));
            t.Start();
        }

        private void ClientThreadStart()
        {
            Socket clientSocket = new Socket(AddressFamily.InterNetwork,
                SocketType.Stream, ProtocolType.Tcp);
            clientSocket.Connect(new IPEndPoint(IPAddress.Parse("127.0.0.1"), 31001));

            while (true)
            {
                clientSocket.Send(Encoding.ASCII.GetBytes(m_fileName));
                System.Threading.Thread.Sleep(21000);

            }
        }
    }
}
