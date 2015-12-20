using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class manage : System.Web.UI.Page
{

    protected void Page_Load(object sender, EventArgs e)
    {

        if (!Page.IsPostBack)
        {
            if (Session["AdminFlag"] == null)
            {
                SqlDataSource1.SelectCommand = "SELECT * FROM [Table] where PAYER_NAME ='" + Session["New"].ToString() + "'";
            }

        }
    }
}