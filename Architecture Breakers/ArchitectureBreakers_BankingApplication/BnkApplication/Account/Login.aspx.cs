using System;
using System.Web;
using System.Web.UI;
using WebSite19;
using BnkApplication.Old_App_Code.Auth;
public partial class Account_Login : Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        RegisterHyperLink.NavigateUrl = "Register";
        OpenAuthLogin.ReturnUrl = Request.QueryString["ReturnUrl"];
        var returnUrl = HttpUtility.UrlEncode(Request.QueryString["ReturnUrl"]);
        if (!String.IsNullOrEmpty(returnUrl))
        {
            RegisterHyperLink.NavigateUrl += "?ReturnUrl=" + returnUrl;
        }
    }

    protected void LogIn(object sender, EventArgs e)
    {

        //System.Diagnostics.Debug.WriteLine("Outside Session ID:" + HttpContext.Current.Session.SessionID);
        if (IsValid)
        {
            LoginService serv = new LoginService(UserName.Text, Password.Text, RememberMe.Checked);
            if (serv.Login()) Response.Redirect("../welcome.aspx");
        }
    }
}