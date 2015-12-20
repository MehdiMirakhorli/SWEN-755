using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Configuration;
using System.IO;
using System.Data.SqlClient;
using System.Data;

namespace WebSite19
{
    public class AccountService
    {
        protected const string BANKDATA = @"C:\Users\Priyank Kapadia\BankInformation";

        public Account GetAccount(String CustID)
        {
            //Retrieve from database
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            SqlCommand cmd = new SqlCommand();
            //SqlDataReader reader;

            string queryString = "SELECT * FROM ACCOUNT where Account_Number=" + CustID;
            SqlDataAdapter adapter = new SqlDataAdapter(queryString, conn);
            DataSet ds = new DataSet();
            conn.Open();
            adapter.Fill(ds);
            conn.Close();
            //cmd.CommandText = "SELECT * FROM ACCOUNT where id=" +CustID;
            //cmd.CommandType = CommandType.Text;
            //cmd.Connection = conn;

            
            //reader = cmd.ExecuteReader();
            Account retrievedAccount = new Account();
            retrievedAccount.AccountName = ds.Tables[0].Rows[0][1].ToString();
            retrievedAccount.ID = ds.Tables[0].Rows[0][3].ToString();
            retrievedAccount.Balance = decimal.Parse(ds.Tables[0].Rows[0][2].ToString());
            return retrievedAccount;
        }

        public Account GetAccountFromFile(string CustID)
        {
            StreamReader file = new StreamReader(Path.Combine(BANK.BANKDATA, BANK.ACCOUNTINFO));
            string line;
            while ((line = file.ReadLine()) != null)
            {
                string[] CharArray = line.Split(';').ToArray<string>();
                if(CharArray.Contains(CustID))
                {
                   
                    Account cust = new Account();
                    cust.ID = CharArray[0];
                    cust.AccountName = CharArray[1];
                    cust.Balance =Decimal.Parse(CharArray[2].Replace("$", ""));
                    return cust;
                }
            }

            return null;
        }
    }
}
