using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebSite19;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System.Data.SqlClient;
using System.Configuration;
using System.Data;

namespace BnkApplication.Old_App_Code.Auth
{
    public class LoginService
    {
        private string _username;

        private string _password;

        private bool _rememberMe;
        public LoginService(string Username, string Password, bool RememberMe)
        {
            _username = Username;
            _password = Password;
            _rememberMe = RememberMe;
        }

        public bool Login()
        {
            var manager = new UserManager();
            ApplicationUser user = manager.Find(_username, _password);
            if (user != null)
            {
                System.Web.HttpContext.Current.Session["New"] = user.UserName;
                System.Web.HttpContext.Current.Session["SESSIONID"] = Guid.NewGuid();
                String sessionID = HttpContext.Current.Session.SessionID;
                System.Diagnostics.Debug.WriteLine("Session ID:" + sessionID);
                System.Diagnostics.Debug.WriteLine("User GUID:" + System.Web.HttpContext.Current.Session["SESSIONID"]);


                IdentityHelper.SignIn(manager, user, _rememberMe);
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
                    string queryString = "SELECT * FROM AspNetUserRoles where UserId='" + user.Id + "'";
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
                    if (Role_Name == "Administrator")
                    {
                        System.Web.HttpContext.Current.Session["AdminFlag"] = "True";
                    }

                    else
                    {
                        System.Web.HttpContext.Current.Session["AdminFlag"] = null;
                    }
                }

                catch (Exception ex)
                {
                    System.Diagnostics.Debug.WriteLine("Exception:" + ex);
                    
                }

                System.Diagnostics.Debug.WriteLine("Session Admin Flag:" + System.Web.HttpContext.Current.Session["AdminFlag"]);
                return true;
            }
            return false;
        }
    }
}