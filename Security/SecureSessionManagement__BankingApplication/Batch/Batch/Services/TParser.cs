using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using Batch.BusinessModel;
using Batch.DataModel;

namespace Batch.Services
{
    class TParser
    {
        public string FileLocation { get; set; }
        
        public TParser(string fileLocation)
        {
            FileLocation = fileLocation;
        }

        public int Parse(TransactionBuilder build)
        {
            StreamReader stream = new StreamReader(FileLocation);
            string line = string.Empty;
            int count = 0;
            int GBI = 0;
            while ((line = stream.ReadLine()) != null)
            {
                if (count != 0)
                {
                    string[] charArray = line.Split(';').ToArray<string>();


                    AccountService service = new AccountService();
                    if (count == 1)
                    {
                        GBI = Int32.Parse(charArray[0]);
                    }
                    string TransactionID = charArray[1];
                    string AccountPayer = charArray[2];
                    string PayerAccountID = charArray[3];
                    string AccountPayee = charArray[4];
                    string PayeeAccountID = charArray[5];
                    string transactiondetails = charArray[6];
                    decimal amount = decimal.Parse(charArray[7].Replace("$", ""));
                    Account PayerAccount = service.GetAccountFromFile(PayerAccountID);
                    Account PayeeAccount = service.GetAccountFromFile(PayeeAccountID);
                    if (PayerAccount != null && PayeeAccount != null)
                    {
                        build.Add(TransactionID, PayeeAccount, PayeeAccount, transactiondetails, amount);
                    }
                }
                count++;
            }
            return GBI;
        }
        public int Parse(TransactionBuilder build,string fileLocation)
        {
            FileLocation = fileLocation;
            return this.Parse(build);
        }
    }
}
