using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Batch.Services;
using Batch.DataModel;
using Batch.BusinessModel;

namespace Batch
{
    class Program
    {
        static void Main(string[] args)
        {
            string file = @"C:\Users\Priyank Kapadia\BankInformation\Batch\batch.txt";
            TParser parse = new TParser(file);
            TransactionBuilder build = new TransactionBuilder();
            int BatchId =  parse.Parse(build);
            TransactionQueue queue =  build.SetBatch(BatchId);
            TransactionService service = new TransactionService();
            service.Start(queue);
            
        }
    }
}
