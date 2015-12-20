using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Batch.BusinessModel
{
    class Transaction
    {
        public Account AccountFrom;

        public Account AccountTo;

        public decimal TrasactionAmt;

        public string Comments;

        public string GTI;

        public bool Credit { get; set; }
        public Transaction(string id,Account accountfrom,Account accountto,string details,decimal Amount)
        {
            GTI = id;
            Comments = details;
            AccountFrom = accountfrom;
            AccountTo = accountto;
            TrasactionAmt = Amount; 
        }
        

    }
}
