using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Batch.BusinessModel;
using Batch.DataModel;

namespace Batch.Services
{
    class TransactionService
    {

       

        public void Start(TransactionQueue tsacs)
        {
            WaitCallback callBack;
            System.Diagnostics.Debug.WriteLine("Setting minimum threads for pool");
            System.Threading.ThreadPool.SetMinThreads(10, 10);
            TransactionQueue splitchunk = new TransactionQueue();
            callBack = new WaitCallback(ProcessBatch);
            for (int i = 0; i < tsacs.Queue.Count; i++)
            {
                splitchunk.Add(tsacs.Queue[i]);
                if(i % 49 == 0 && i != 0)
                {
                    System.Threading.ThreadPool.QueueUserWorkItem(ProcessBatch,(object)splitchunk);
                    splitchunk = null;
                    splitchunk = new TransactionQueue();
                }
            }
        }

        public void ProcessBatch(object tsacs)
        {
            
            TransactionQueue queue = (TransactionQueue)tsacs;
            foreach (Transaction item in queue)
            {
                ProcessTransaction(item);
            }
        }

        public void ProcessTransaction(Transaction unitTrans)
        {
            unitTrans.AccountFrom.Balance = unitTrans.AccountFrom.Balance - unitTrans.TrasactionAmt;
            unitTrans.AccountTo.Balance = unitTrans.AccountTo.Balance + unitTrans.TrasactionAmt;
            System.Diagnostics.Debug.WriteLine("Is pool thread: {0}, Hash: {1}", Thread.CurrentThread.IsThreadPoolThread, Thread.CurrentThread.GetHashCode());
        }
    }
}
