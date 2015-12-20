using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data.SqlClient;
using System.Configuration;

public partial class Default2 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void Button1_Click(object sender, EventArgs e)
    {
        try
        {
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            conn.Open();
            string insertQuery = "insert into [Table] ([name],[account],[amount]) values (@name1,@account1,@amount1)";
            SqlCommand com = new SqlCommand(insertQuery, conn);
            com.Parameters.AddWithValue("@name1", TextBox3.Text);
            com.Parameters.AddWithValue("@account1", TextBox1.Text);
            com.Parameters.AddWithValue("@amount1", TextBox2.Text);
            com.ExecuteNonQuery();
            Response.Redirect("manage.aspx");
            Response.Write("Transfer Successfull");
            conn.Close();
        }

        catch (Exception ex)
        {
            Response.Write("Error:"+ex.ToString());
        }
    }

}