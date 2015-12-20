<script runat="server">

    Protected Sub Page_Load(sender As Object, e As EventArgs)

    End Sub
</script>

<form id="form1" runat="server">
    <asp:gridview runat="server" AutoGenerateColumns="False" DataKeyNames="name" DataSourceID="SqlDataSource1" Height="225px" Width="379px">
        <Columns>
            <asp:BoundField DataField="name" HeaderText="name" ReadOnly="True" SortExpression="name" />
            <asp:BoundField DataField="account" HeaderText="account" SortExpression="account" />
            <asp:BoundField DataField="amount" HeaderText="amount" SortExpression="amount" />
            <asp:BoundField DataField="status" HeaderText="status" SortExpression="status" />
        </Columns>
    </asp:gridview>
    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="SELECT * FROM [Table]"></asp:SqlDataSource>
</form>
