namespace WebSite19
{
    public class Transaction
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
