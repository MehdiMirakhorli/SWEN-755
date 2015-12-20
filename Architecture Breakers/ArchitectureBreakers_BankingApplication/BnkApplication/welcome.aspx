<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" Inherits="_welcome" Codebehind="welcome.aspx.cs" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">

    <div class="jumbotron">
        <h2 id="welcome_label">
            <asp:Label ID="Label1" runat="server" Text="Welcome to"></asp:Label>
        </h2>
        <p class="lead">Transfer money to anywhere in the world</p>
        <p><a href="transfer.aspx" class="btn btn-primary btn-lg">Transfer Amount &raquo;</a></p>
    </div>

    
</asp:Content>
