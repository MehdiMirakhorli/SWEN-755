using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bnk.ClientApplication
{
    class Program
    {
        [STAThread]
        
        static void Main(string[] args)
        {
            TCPClient client = null;
            client = new TCPClient("Alive\n");
            
        }
    }
}
