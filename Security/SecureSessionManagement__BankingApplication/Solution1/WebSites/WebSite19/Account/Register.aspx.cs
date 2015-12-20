using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using System;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web.Security;
using System.Web.UI;
using WebSite19;

public partial class Account_Register : Page
{
    protected void CreateUser_Click(object sender, EventArgs e)
    {
        var manager = new UserManager();
        
        var newRole = "AccHolder";
        var roleManager = new RoleManager<IdentityRole>(new RoleStore<IdentityRole>());
        IdentityResult ir = roleManager.Create(new IdentityRole(newRole));    
        ////var newRoleUser = "AccHolder";
        
        var user = new ApplicationUser() { UserName = UserName.Text };
        IdentityResult result = manager.Create(user, Password.Text);
        if (result.Succeeded)
        {  
            System.Diagnostics.Debug.WriteLine("User:"+ UserName.Text);
            var retreive = new UserManager();
            var storedUser = retreive.FindByName(UserName.Text);        
            IdentityResult addRoles = manager.AddToRole(storedUser.Id, newRole);
            Session["New"] = UserName.Text;
            Session["SESSIONID"] = Guid.NewGuid();
            System.Diagnostics.Debug.WriteLine("User ID:" + storedUser.Id);
            try
            {   
                SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
                conn.Open();
                string insertQuery = "insert into [ACCOUNT] ([Id],[name],[balance],[Account_Number]) values (@id,@name1,@balance1,@account1)";
                SqlCommand com = new SqlCommand(insertQuery, conn);
                com.Parameters.AddWithValue("@id", storedUser.Id);
                com.Parameters.AddWithValue("@name1", UserName.Text);           
                com.Parameters.AddWithValue("@balance1", "100000000");
                com.Parameters.AddWithValue("@account1", "1");
                com.ExecuteNonQuery();
                conn.Close();
                System.Diagnostics.Debug.WriteLine("Inside User:" + UserName.Text);
            }

            catch (Exception ex)
            {
                Response.Write("Error:" + ex.ToString());
            }


            IdentityHelper.SignIn(manager, user, isPersistent: false);
            //IdentityHelper.RedirectToReturnUrl(Request.QueryString["ReturnUrl"], Response);  
            Response.Redirect("../welcome.aspx");
        }
        else
        {
            ErrorMessage.Text = result.Errors.FirstOrDefault();
        }
    }
}