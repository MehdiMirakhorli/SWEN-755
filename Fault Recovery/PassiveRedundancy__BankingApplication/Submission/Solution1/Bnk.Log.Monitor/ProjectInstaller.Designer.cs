namespace Bnk.Log.Monitor
{
    partial class ProjectInstaller
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary> 
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Component Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.monitorservice = new System.ServiceProcess.ServiceProcessInstaller();
            this.monitorServiceInstaller = new System.ServiceProcess.ServiceInstaller();
            // 
            // monitorservice
            // 
            this.monitorservice.Account = System.ServiceProcess.ServiceAccount.LocalSystem;
            this.monitorservice.Password = null;
            this.monitorservice.Username = null;
            // 
            // monitorServiceInstaller
            // 
            this.monitorServiceInstaller.Description = "Monitor for Log entries";
            this.monitorServiceInstaller.DisplayName = "monitor Service";
            this.monitorServiceInstaller.ServiceName = "Service1";
            // 
            // ProjectInstaller
            // 
            this.Installers.AddRange(new System.Configuration.Install.Installer[] {
            this.monitorservice,
            this.monitorServiceInstaller});

        }

        #endregion

        private System.ServiceProcess.ServiceProcessInstaller monitorservice;
        private System.ServiceProcess.ServiceInstaller monitorServiceInstaller;
    }
}