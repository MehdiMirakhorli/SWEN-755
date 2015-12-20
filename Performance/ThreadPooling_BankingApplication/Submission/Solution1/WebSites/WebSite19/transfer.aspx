<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="transfer.aspx.cs" Inherits="Default2" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="jumbotron">
        <p class="lead"></p>
        <!-- <p><a href="http://www.asp.net" class="btn btn-primary btn-lg">Learn more &raquo;</a><asp:Label ID="Label1" runat="server" Text="Label"></asp:Label> -->
 
        <table class="nav-justified" style="width: 52%; margin-bottom: 73px; height: 310px;">
            <tr>
                <td class="text-left"><asp:Label ID="HEADER" runat="server" Text="Transfer Amount" Font-Bold="True" Font-Size="X-Large" Font-Underline="True"></asp:Label></td>
                <td class="text-right">
                    &nbsp;</td>
            </tr>
            <tr>
                <td class="text-right"><asp:Label ID="amount" runat="server" Text="Upload Batch File:"></asp:Label></td>
                <td style="padding-left: 20px;" class="text-right" dir="ltr">
                    <asp:FileUpload ID="FileUpload1" runat="server" CssClass="col-md-8" style="margin-left: 23px" Width="523px" Font-Size="Smaller" />
                </td>
            </tr>
            <tr>
                <td class="text-right">&nbsp;</td>
                <td class="text-right">
                    <asp:Label ID="STATUS" runat="server"></asp:Label>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <asp:Button ID="VIEW_STATUS" runat="server" OnClick="VIEW_STATUS_Click" Text="View Status" Visible="False" />
                    </td>
                <td class="text-right" dir="auto">
                    <asp:Button ID="Button1" runat="server" OnClick="Button1_Click" Text="Submit" />
                    &nbsp;
                    </td>
            </tr>
        </table>
    </div>

    
</asp:Content>
