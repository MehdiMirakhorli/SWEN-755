using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
namespace WebSite19
{
    public class TransactionBuilder
    {
        private TransactionQueue BnkQueue = new TransactionQueue();

        public bool Add(Transaction trans)
        {
            BnkQueue.Add(trans);
            return true;
        }
        public bool Add(string transindex,Account PayerAccount ,Account PayeeAccount,string details,decimal amount)
        {
            BnkQueue.Add(new Transaction(transindex,PayerAccount,PayeeAccount,details,amount));
            return true;
        }

        public TransactionQueue SetBatch(int ID)
        {
            BnkQueue.BatchID = ID;
            return BnkQueue;
        }
    }
}
