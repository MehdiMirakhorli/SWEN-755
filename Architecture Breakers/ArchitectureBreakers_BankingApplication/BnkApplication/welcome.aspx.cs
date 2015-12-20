using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
public partial class _welcome : Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["New"] != null)
        {
            Label1.Text = "Welcome "+Session["New"].ToString();
        }
        else
        {
            Response.Redirect("Account/Login.aspx");
        }
    }
}