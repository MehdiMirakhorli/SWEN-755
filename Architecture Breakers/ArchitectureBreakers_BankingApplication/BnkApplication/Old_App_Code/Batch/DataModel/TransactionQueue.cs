using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebSite19
{
    public class TransactionQueue:IEnumerable<Transaction>
    {
        public IList<Transaction> Queue = new List<Transaction>();

        public int BatchID{set; get;}
    
        public IEnumerator<Transaction> GetEnumerator()
        {
            return Queue.GetEnumerator();
        }

        System.Collections.IEnumerator System.Collections.IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        internal void Add(Transaction unittransaction)
        {
            Queue.Add(unittransaction);
        }
    }
}
