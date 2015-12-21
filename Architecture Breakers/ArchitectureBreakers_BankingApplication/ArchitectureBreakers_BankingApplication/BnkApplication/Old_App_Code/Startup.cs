using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(WebSite19.Startup))]
namespace WebSite19
{
    public partial class Startup {
        public void Configuration(IAppBuilder app) {
            System.Diagnostics.Debug.WriteLine("STARTUP");
            //TCPClient client = null;
            //client = new TCPClient("Alive");
            ConfigureAuth(app);
        }
    }
}
