using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;


namespace WebSite19
{
    public class TransactionService
    {


        Account MasterAccount = new Account();
        public void Start(TransactionQueue tsacs)
        {
            WaitCallback callBack;
            System.Diagnostics.Debug.WriteLine("Setting minimum threads for pool");
            System.Threading.ThreadPool.SetMinThreads(10, 10);
            TransactionQueue splitchunk = new TransactionQueue();
            callBack = new WaitCallback(ProcessBatch);
            MasterAccount = tsacs.Queue[0].AccountFrom;
            for (int i = 1; i <= tsacs.Queue.Count; i++)
            {
                splitchunk.Add(tsacs.Queue[i - 1]);
                if(i % 500 == 0 ||  i == tsacs.Queue.Count)
                {
                    splitchunk.BatchID = tsacs.BatchID;
                    System.Threading.ThreadPool.QueueUserWorkItem(callBack,(object)splitchunk);
                    splitchunk = null;
                    splitchunk = new TransactionQueue();
                }
            }
        }

        public void ProcessBatch(object tsacs)
        {
            
            TransactionQueue queue = (TransactionQueue)tsacs;
            int BatchID = queue.BatchID;
            foreach (Transaction item in queue)
            {
                ProcessTransaction(item,BatchID);
            }
        }

        public void ProcessTransaction(Transaction unitTrans,int BatchID)
        {
            //unitTrans.AccountFrom.Balance = unitTrans.AccountFrom.Balance - unitTrans.TrasactionAmt;
            //unitTrans.AccountTo.Balance = unitTrans.AccountTo.Balance + unitTrans.TrasactionAmt;
           
            int GBI = BatchID;
            int GTI = Int32.Parse(unitTrans.GTI);
            String PayerName = unitTrans.AccountFrom.AccountName;
            int PayerAccount = Int32.Parse(unitTrans.AccountFrom.ID);
            String PayeeName = unitTrans.AccountTo.AccountName;
            int PayeeAccount = Int32.Parse(unitTrans.AccountTo.ID);
            String TrxDetails = unitTrans.Comments;
            Decimal Amount = unitTrans.TrasactionAmt;
            SqlConnection conn = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            conn.Open();
            string insertQuery = "insert into [Table] WITH (ROWLOCK) ([GBI],[GTI],[Payer_Name],[Payer_Account],[Payee_Name],[Payee_Account],[TRX_Details],[Amount],[Status]) values (@GBI,@GTI,@Payer_Name,@Payer_Account,@Payee_Name,@Payee_Account,@Trx_Details,@Amount,@Status)";
            SqlCommand com = new SqlCommand(insertQuery, conn);
            com.Parameters.AddWithValue("@GBI", GBI);
            com.Parameters.AddWithValue("@GTI", GTI);
            com.Parameters.AddWithValue("@Payer_Name", PayerName);
            com.Parameters.AddWithValue("@Payer_Account", PayerAccount);
            com.Parameters.AddWithValue("@Payee_Name", PayeeName);
            com.Parameters.AddWithValue("@Payee_Account", PayeeAccount);
            com.Parameters.AddWithValue("@Trx_Details", TrxDetails);
            com.Parameters.AddWithValue("@Amount", Amount);
            com.Parameters.AddWithValue("@Status", "Processed");
            com.ExecuteNonQuery();
            conn.Close();
            System.Diagnostics.Debug.WriteLine("Inserting Transaction");


            ////Master table Payer Account Retrieval
            //SqlConnection conn_retrieve1 = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            //SqlCommand cmd = new SqlCommand();
            //string queryString = "SELECT * FROM ACCOUNT where Account_Number=" + PayerAccount;
            //SqlDataAdapter adapter = new SqlDataAdapter(queryString, conn_retrieve1);
            //DataSet ds = new DataSet();
            //conn_retrieve1.Open();
            //adapter.Fill(ds);
            //conn_retrieve1.Close();
            ////cmd.CommandText = "SELECT * FROM ACCOUNT where id=" +CustID;
            ////cmd.CommandType = CommandType.Text;
            ////cmd.Connection = conn;
            ////reader = cmd.ExecuteReader();
            //Account retrievedAccount = new Account();
            //retrievedAccount.AccountName = ds.Tables[0].Rows[0][1].ToString();
            //retrievedAccount.ID = ds.Tables[0].Rows[0][3].ToString();
            //retrievedAccount.Balance = decimal.Parse(ds.Tables[0].Rows[0][2].ToString());
            //Decimal Post_Payer_Amount = retrievedAccount.Balance - Amount;
            //conn_retrieve1.Close();

            //Master Table Payer Account Updation
            lock(MasterAccount) 
            {
                MasterAccount.Balance = MasterAccount.Balance - unitTrans.TrasactionAmt;
                SqlConnection conn_update1 = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
                conn_update1.Open();
                string updateQuery1 = "Update ACCOUNT WITH (ROWLOCK) SET Balance = @Post_Payer_Amount where (Account_Number = @PayerAccount)";
                SqlCommand com_update1 = new SqlCommand(updateQuery1, conn_update1);
                com_update1.Parameters.AddWithValue("@PayerAccount", PayerAccount);
                com_update1.Parameters.AddWithValue("@Post_Payer_Amount", MasterAccount.Balance);
                com_update1.ExecuteNonQuery();
                conn_update1.Close();
            }
            

            //Master table Payer Account Retrieval
            //SqlConnection conn_retrieve2 = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            //SqlCommand cmd_retrieve2 = new SqlCommand();
            //SqlDataReader reader_retrieve2;

            //cmd_retrieve2.CommandText = "SELECT * FROM ACCOUNT where Account_Number=" + PayerAccount;
            //cmd_retrieve2.CommandType = CommandType.Text;
            //cmd_retrieve2.Connection = conn_retrieve2;
            //conn_retrieve2.Open();
            //reader_retrieve2 = cmd_retrieve2.ExecuteReader();
            //Account retrieved_Payee_Account = new Account();
            //retrieved_Payee_Account.AccountName = reader_retrieve2["Name"].ToString();
            //retrieved_Payee_Account.ID = reader_retrieve2["Account_Number"].ToString();
            //retrieved_Payee_Account.Balance = decimal.Parse(reader_retrieve2["Balance"].ToString());
            //Decimal Post_Payee_Amount = retrieved_Payee_Account.Balance + Amount;
            //conn_retrieve2.Close();

            //Master Table Payee Account Updation
            unitTrans.AccountTo.Balance = unitTrans.AccountTo.Balance + unitTrans.TrasactionAmt;
            SqlConnection conn_update2 = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            conn_update2.Open();
            string updateQuery2 = "Update ACCOUNT WITH (ROWLOCK) SET Balance = @Post_Payee_Amount where (Account_Number = @PayeeAccount)";
            SqlCommand com_update2 = new SqlCommand(updateQuery2, conn_update2);
            com_update2.Parameters.AddWithValue("@PayeeAccount", PayeeAccount);
            com_update2.Parameters.AddWithValue("@Post_Payee_Amount", unitTrans.AccountTo.Balance);
            com_update2.ExecuteNonQuery();
            conn_update2.Close();
            //System.Diagnostics.Debug.WriteLine("Transaction Processing Complete TRX_ID="+ GTI);

            System.Diagnostics.Debug.WriteLine("Is pool thread: {0}, Hash: {1}", Thread.CurrentThread.IsThreadPoolThread, Thread.CurrentThread.GetHashCode());
        }
    }
}
