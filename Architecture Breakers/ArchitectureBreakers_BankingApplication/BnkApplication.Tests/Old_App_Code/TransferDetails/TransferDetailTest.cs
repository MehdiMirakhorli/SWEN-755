using Microsoft.VisualStudio.TestTools.UnitTesting;
using BnkApplication.Old_App_Code.TransferDetails;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Moq;
using Microsoft.Owin.Security;
using System.Web;
using WebSite19;
using BnkApplication.Old_App_Code.Auth;
using System.IO;
using System.Web.SessionState;
using System.Reflection;

namespace BnkApplication.Old_App_Code.TransferDetails.Tests
{
    [TestClass()]
    public class TransferDetailServiceTests
    {
        [TestMethod()]
        public void TransferDetailServiceTest()
        {
            string Username = "admin";
            string PassWord = "1234";
            var mockAuthenticationManager = new Mock<IAuthenticationManager>();
            mockAuthenticationManager.Setup(am => am.SignOut());
            mockAuthenticationManager.Setup(am => am.SignIn());
            IdentityHelper.AuthenticationManager = mockAuthenticationManager.Object;

            HttpContext.Current = FakeHttpContext();
            AppDomain.CurrentDomain.SetData(
                                    "DataDirectory", @"C:\Users\Priyank Kapadia\Documents\Visual Studio 2015\Projects\BnkApplication\BnkApplication\App_Data\");
            
            bool RememberME = true;
            LoginService servicetest = new LoginService(Username, PassWord, RememberME);
            servicetest.Login();
            // Role Authentication the user who is not admin should not be able to view all details

            // faking the object as in the UI
            System.Web.UI.WebControls.SqlDataSource sql = new System.Web.UI.WebControls.SqlDataSource();
            sql.SelectCommand = "SELECT * FROM [Table]";

            TransferDetailService testserv = new TransferDetailService(sql,Username);
            Assert.AreEqual("SELECT * FROM [Table] where PAYER_NAME ='" + Username + "'", sql.SelectCommand);
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