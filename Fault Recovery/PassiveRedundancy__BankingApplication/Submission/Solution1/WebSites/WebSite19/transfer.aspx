<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="transfer.aspx.cs" Inherits="Default2" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="jumbotron">
        <p class="lead"></p>
        <!-- <p><a href="http://www.asp.net" class="btn btn-primary btn-lg">Learn more &raquo;</a><asp:Label ID="Label1" runat="server" Text="Label"></asp:Label> -->
 
        <table class="nav-justified" style="width: 52%">
            <tr>
                <td class="text-right" style="width: 133px"><asp:Label ID="name" runat="server" Text="Name"></asp:Label></td>
                <td class="text-right">
                    <asp:TextBox ID="TextBox3" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="text-right" style="width: 133px"><asp:Label ID="account" runat="server" Text="Account"></asp:Label></td>
                <td class="text-right">
                    <asp:TextBox ID="TextBox1" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="text-right" style="width: 133px; height: 46px;"><asp:Label ID="amount" runat="server" Text="Amount"></asp:Label></td>
                <td style="height: 46px" class="text-right">
                    <asp:TextBox ID="TextBox2" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="text-right" style="width: 133px; height: 46px;">&nbsp;</td>
                <td style="height: 46px" class="text-right">
                    &nbsp;</td>
            </tr>
            <tr>
                <td class="text-right" style="width: 133px; height: 46px;">&nbsp;</td>
                <td style="height: 46px" class="text-right">
                    <asp:Button ID="Button1" runat="server" OnClick="Button1_Click" Text="Submit" />
                    &nbsp;
                    </td>
            </tr>
        </table>
    </div>

    
</asp:Content>
