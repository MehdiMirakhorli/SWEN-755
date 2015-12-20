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
            System.Diagnostics.EventLog.WriteEntry("HeartBeat Monitor", "Service Initialized");
        }
            

        protected override void OnStart(string[] args)
        {
            m_receive = new HeartBeatReceiver();
            m_receive.StartServer();
            System.Diagnostics.EventLog.WriteEntry("HeartBeat Monitor", "Service Start Event Triggered");
        }

        protected override void OnStop()
        {
            m_receive.StopServer();
            System.Diagnostics.EventLog.WriteEntry("HeartBeat Monitor", "Service Stop Event closing all connections");
        }
    }
}
