<%@ Page Title="Manage Account" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" Inherits="manage" Codebehind="manage.aspx.cs" %>
<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <asp:gridview runat="server" AutoGenerateColumns="False" DataSourceID="SqlDataSource1" Height="100px" Width="379px" CellPadding="0" HorizontalAlign="Center" PageSize="2000">
        <Columns>
            <asp:BoundField DataField="PAYER_NAME" HeaderText="PAYER_NAME" ReadOnly="True" SortExpression="PAYER_NAME" />
            <asp:BoundField DataField="PAYER_ACCOUNT" HeaderText="PAYER_ACCOUNT" SortExpression="PAYER_ACCOUNT" />
            <asp:BoundField DataField="PAYEE_NAME" HeaderText="PAYEE_NAME" SortExpression="PAYEE_NAME" />
            <asp:BoundField DataField="PAYEE_ACCOUNT" HeaderText="PAYEE_ACCOUNT" SortExpression="PAYEE_ACCOUNT" />
            <asp:BoundField DataField="GBI" HeaderText="GBI" SortExpression="GBI" />
            <asp:BoundField DataField="GTI" HeaderText="GTI" SortExpression="GTI" />
            <asp:BoundField DataField="TRX_Details" HeaderText="TRX_Details" SortExpression="TRX_Details" />
            <asp:BoundField DataField="AMOUNT" HeaderText="AMOUNT" SortExpression="AMOUNT" />
            <asp:BoundField DataField="STATUS" HeaderText="STATUS" SortExpression="STATUS" />
        </Columns>
        <EditRowStyle HorizontalAlign="Center" VerticalAlign="Middle" />
        <EmptyDataRowStyle HorizontalAlign="Center" VerticalAlign="Middle" />
    </asp:gridview>
    <asp:SqlDataSource ID="SqlDataSource1" runat="server" ConnectionString="<%$ ConnectionStrings:ConnectionString %>" SelectCommand="SELECT * FROM [Table]"></asp:SqlDataSource>

    </asp:Content>