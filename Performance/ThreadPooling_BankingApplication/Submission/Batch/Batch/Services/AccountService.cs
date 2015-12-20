using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Batch.BusinessModel;
using System.Configuration;
using System.IO;
namespace Batch.Services
{
    class AccountService
    {
        protected const string BANKDATA = @"C:\Users\Priyank Kapadia\BankInformation";

        public Account GetAccount(int CustID)
        {
            //Retrieve from database
            return new Account();
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
