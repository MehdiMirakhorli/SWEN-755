using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Web.Administration;

namespace Bnk.Log.Monitor.Areas.FaultPrevention
{
    class WebSiteControl
    {
        public void StartWebsite(string WebsiteName)
        {
            var server = new ServerManager();
            var site = server.Sites.FirstOrDefault(s => s.Name == WebsiteName);
            if (site != null)
            {
                //restart the site...
                site.Start();
            }
        }

        public void StopWebsite(string WebsiteName)
        {
            var server = new ServerManager();
            var site = server.Sites.FirstOrDefault(s => s.Name == WebsiteName);
            if (site != null)
            {
                //restart the site...
                site.Stop();
            }
        }
    }
}
