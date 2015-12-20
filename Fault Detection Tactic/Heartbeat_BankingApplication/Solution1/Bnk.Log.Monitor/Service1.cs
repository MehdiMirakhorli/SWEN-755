using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;
using Bnk.Log.Monitor.Areas.HeartBeat;

namespace Bnk.Log.Monitor
{
    public partial class Service1 : ServiceBase
    {
        HeartBeatReceiver m_receive;
        System.Diagnostics.EventLog eventLog1;
        public Service1()
        {
            InitializeComponent();
            eventLog1 = new System.Diagnostics.EventLog();
            if (!System.Diagnostics.EventLog.SourceExists("MySource"))
            {
                System.Diagnostics.EventLog.CreateEventSource(
                    "MySource", "MyNewLog");
            }
            eventLog1.Source = "MySource";
            eventLog1.Log = "MyNewLog";
            System.Diagnostics.EventLog.WriteEntry("Please","hello");

        }
        [Conditional("DEBUG_SERVICE")]
        private static void DebugMode()
        {
            Debugger.Break();
        }

        protected override void OnStart(string[] args)
        {
            eventLog1.WriteEntry("In OnStart");
            System.Diagnostics.EventLog.WriteEntry("Please", "hello2");
            m_receive = new HeartBeatReceiver();
            System.Diagnostics.EventLog.WriteEntry("dedasf", "afasf");

            m_receive.StartServer();
            System.Diagnostics.EventLog.WriteEntry("done", "done");

        }

        protected override void OnStop()
        {
        }
    }
}
