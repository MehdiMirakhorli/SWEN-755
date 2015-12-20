using Microsoft.VisualStudio.TestTools.UnitTesting;
using BnkApplication.Old_App_Code.Auth;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Web;
using System.Web.Hosting;
using System.Web.SessionState;
using System.Reflection;
using WebSite19;
using Microsoft.Owin.Security;
using Moq;

namespace BnkApplication.Old_App_Code.Auth.Tests
{
    [TestClass()]
    public class UnitTest1
    {
        [TestMethod()]
        public void LoginServiceTest()
        {
            
            var mockAuthenticationManager = new Mock<IAuthenticationManager>();
            mockAuthenticationManager.Setup(am => am.SignOut());
            mockAuthenticationManager.Setup(am => am.SignIn());
            IdentityHelper.AuthenticationManager = mockAuthenticationManager.Object;
            
            HttpContext.Current = FakeHttpContext();
            AppDomain.CurrentDomain.SetData(
                                    "DataDirectory", @"C:\Users\Priyank Kapadia\Documents\Visual Studio 2015\Projects\BnkApplication\BnkApplication\App_Data\");
            string Username = "admin";
            string Password = "test1234";
            bool RememberME = true;
            LoginService servicetest = new LoginService(Username,Password,RememberME);
            servicetest.Login();
            string sessionId = System.Web.HttpContext.Current.Session["SessionID"].ToString();

            Username = "SMTest1";
            Password = "test1234";
            RememberME = true;
            servicetest = new LoginService(Username, Password, RememberME);
            servicetest.Login();
            string sessionId2 = System.Web.HttpContext.Current.Session["SessionID"].ToString();
            Assert.AreNotEqual(sessionId, sessionId2);
         }
        public static HttpContext FakeHttpContext()
        {
            var httpRequest = new HttpRequest("", "http://stackoverflow/", "");
            var stringWriter = new StringWriter();
            var httpResponse = new HttpResponse(stringWriter);
            var httpContext = new HttpContext(httpRequest, httpResponse);

            var sessionContainer = new HttpSessionStateContainer("id", new SessionStateItemCollection(),
                                                    new HttpStaticObjectsCollection(), 10, true,
                                                    HttpCookieMode.AutoDetect,
                                                    SessionStateMode.InProc, false);

            httpContext.Items["AspSession"] = typeof(HttpSessionState).GetConstructor(
                                        BindingFlags.NonPublic | BindingFlags.Instance,
                                        null, CallingConventions.Standard,
                                        new[] { typeof(HttpSessionStateContainer) },
                                        null)
                                .Invoke(new object[] { sessionContainer });

            return httpContext;
        }
    }
    
}