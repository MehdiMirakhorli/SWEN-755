using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using BnkApplication.Old_App_Code.TransferDetails;
public partial class manage : System.Web.UI.Page
{

    protected void Page_Load(object sender, EventArgs e)
    {

        if (!Page.IsPostBack)
        {
            TransferDetailService serv = new TransferDetailService(SqlDataSource1, Session["New"].ToString());
        }
    }
}