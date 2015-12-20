using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BnkApplication.Old_App_Code.TransferDetails
{
    public class TransferDetailService
    {
        public TransferDetailService(System.Web.UI.WebControls.SqlDataSource sql,string name)
        {
            //if (System.Web.HttpContext.Current.Session["AdminFlag"] == null) // This part is modified to see if that 
            //{

                //sql.SelectCommand = "SELECT * FROM [Table] where PAYER_NAME ='" + name + "'";
            //}
        }
    }
}