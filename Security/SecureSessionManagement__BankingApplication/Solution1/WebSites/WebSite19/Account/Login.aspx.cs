using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.Owin.Security;
using System;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Web;
using System.Web.UI;
using WebSite19;

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
                // Validate the user password
                var manager = new UserManager();
                ApplicationUser user = manager.Find(UserName.Text, Password.Text);
                if (user != null)
                {
                Session["New"] = user.UserName;
                Session["SESSIONID"] = Guid.NewGuid();
                String sessionID = HttpContext.Current.Session.SessionID;
                System.Diagnostics.Debug.WriteLine("Session ID:" + sessionID);
                System.Diagnostics.Debug.WriteLine("User GUID:" + Session["SESSIONID"]);


                IdentityHelper.SignIn(manager, user, RememberMe.Checked);
                //IdentityHelper.RedirectToReturnUrl(Request.QueryString["ReturnUrl"], Response);
                var roleManager = new RoleManager<IdentityRole>(new RoleStore<IdentityRole>());
                var Test1 = roleManager.FindById(user.Id);
                var Test2 = roleManager.FindByName(user.UserName);
                //IdentityResult addRoles = manager.AddToRole(storedUser.Id, newRole);
                //var Test3 = manager.GetRoles(user.UserName);
                //var Test4 = manager.IsInRole(user.Id,"Administrator");
                //var Test5 = manager.GetRoles(user.Id);
                //var Test = HttpContext.Current.User.IsInRole("Administrator");
                //Retrieve Role ID
                try
                {
                    //Retrieve from database
                    SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["DefaultConnection"].ConnectionString);
                    SqlCommand cmd = new SqlCommand();
                    //SqlDataReader reader;
                    string queryString = "SELECT * FROM AspNetUserRoles where UserId='" + user.Id+"'";
                    SqlDataAdapter adapter = new SqlDataAdapter(queryString, conn);
                    DataSet ds = new DataSet();
                    conn.Open();
                    adapter.Fill(ds);
                    conn.Close();
                    var Role_ID = ds.Tables[0].Rows[0][1].ToString();

                    string fetchRoleName = "SELECT * FROM AspNetRoles where Id='" + Role_ID + "'";
                    SqlDataAdapter fetchRoleNameadapter = new SqlDataAdapter(fetchRoleName, conn);
                    DataSet fetchRoleNameds = new DataSet();
                    conn.Open();
                    fetchRoleNameadapter.Fill(fetchRoleNameds);
                    conn.Close();
                    var Role_Name = fetchRoleNameds.Tables[0].Rows[0][1].ToString();
                    System.Diagnostics.Debug.WriteLine("Role_Name:" + Role_Name);
                    if(Role_Name == "Administrator")
                    {
                        Session["AdminFlag"] = "True";
                    }

                    else
                    {
                        Session["AdminFlag"] = null;
                    }
                }

                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine("Exception:" + ex);

                }

                System.Diagnostics.Debug.WriteLine("Session Admin Flag:" + Session["AdminFlag"]);
                Response.Redirect("../welcome.aspx");
                }
                else
                {
                    FailureText.Text = "Invalid username or password.";
                    ErrorMessage.Visible = true;
                }
            }
        }
}