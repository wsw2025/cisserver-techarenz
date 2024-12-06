/*
 * File: CIServer.java
 * ------------------------------
 * When it is finished, this program will implement a basic
 * ecommerce network management server.  Remember to update this comment!
 */

package edu.cis.Controller;

import acm.program.*;
import edu.cis.Model.*;
import edu.cis.Utils.SimpleServer;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Double.parseDouble;

public class CIServer extends ConsoleProgram
        implements SimpleServerListener
{

    /* The internet port to listen to requests on */
    private static final int PORT = 8000;

    /* The server object. All you need to do is start it */
    private SimpleServer server = new SimpleServer(this, PORT);

    private ArrayList<CISUser> users = new ArrayList<CISUser>();
    private Menu menu = new Menu();

    /**
     * Starts the server running so that when a program sends
     * a request to this server, the method requestMade is
     * called.
     */
    public void run()
    {
        println("Starting server on port " + PORT);
        server.start();
    }

    /**
     * When a request is sent to this server, this method is
     * called. It must return a String.
     *
     * @param request a Request object built by SimpleServer from an
     *                incoming network request by the client
     */
    public String requestMade(Request request)
    {
        String cmd = request.getCommand();
        println(request.toString());

        // your code here.
        if (request.getCommand().equals(CISConstants.PING))
        {
            final String PING_MSG = "Hello, internet";

            //println is used instead of System.out.println to print to the server GUI
            println("   => " + PING_MSG);
            return PING_MSG;
        }
        if(request.getCommand().equals(CISConstants.CREATE_USER))
        {
            return createUser(request);
        }
        if(request.getCommand().equals(CISConstants.ADD_MENU_ITEM))
        {
            return addMenuItem(request);
        }
        if(request.getCommand().equals(CISConstants.PLACE_ORDER))
        {
            return placeOrder(request);
        }
        if(request.getCommand().equals(CISConstants.DELETE_ORDER))
        {
            return deleteOrder(request);
        }
        if(request.getCommand().equals(CISConstants.GET_ORDER))
        {
            return getOrder(request);
        }
        if(request.getCommand().equals(CISConstants.GET_ITEM))
        {
            return getItem(request);
        }
        if(request.getCommand().equals(CISConstants.GET_USER))
        {
            return getUser(request);
        }
        if(request.getCommand().equals(CISConstants.GET_CART))
        {
            return getCart(request);
        }

        return "Error: Unknown command " + cmd + ".";
    }

    public String createUser(Request req){
        if(req.getParam(CISConstants.USER_ID_PARAM)==null || req.getParam(CISConstants.USER_NAME_PARAM)==null || req.getParam(CISConstants.YEAR_LEVEL_PARAM)==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for (CISUser u: users){
            if(u.getUserID().equals(req.getParam(CISConstants.USER_ID_PARAM))){
                return CISConstants.DUP_USER_ERR;
            }
        }
        CISUser newUser = new CISUser(req.getParam(CISConstants.USER_ID_PARAM), req.getParam(CISConstants.USER_NAME_PARAM), req.getParam(CISConstants.YEAR_LEVEL_PARAM));
        users.add(newUser);
        return CISConstants.SUCCESS;
    }

    public String addMenuItem(Request req){
        String item_name = req.getParam(CISConstants.ITEM_NAME_PARAM);
        String desc = req.getParam(CISConstants.DESC_PARAM);
        String price = req.getParam(CISConstants.PRICE_PARAM);
        String id = req.getParam(CISConstants.ITEM_ID_PARAM);
        String type = req.getParam(CISConstants.ITEM_TYPE_PARAM);

        if(item_name==null||desc==null||price==null||id==null||type==null){
            return CISConstants.PARAM_MISSING_ERR;
        }
        for(MenuItem i : menu.getEatriumItems()){
            if(i.getId().equals(id)){
                return CISConstants.DUP_ITEM_ERR;
            }
        }
        MenuItem item = new MenuItem(item_name, desc, parseDouble(price), id, type);
        ArrayList<MenuItem> curMenu = menu.getEatriumItems();
        curMenu.add(item);
        menu.setEatriumItems(curMenu);

        return CISConstants.SUCCESS;
    }

    public String placeOrder(Request req){
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        String menuItemID = req.getParam(CISConstants.ITEM_ID_PARAM);
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String orderType = req.getParam(CISConstants.ORDER_TYPE_PARAM);

        if(orderID==null || menuItemID==null || userID==null || orderType==null) return CISConstants.PARAM_MISSING_ERR;
        
        CISUser user = null;

        boolean userExists = false;
        for (CISUser u: users){
            if(u.getUserID().equals(userID)){
                userExists = true;
                user = u;
                break;
            }
        }
        if(!userExists) return CISConstants.USER_INVALID_ERR;

        for (Order o: user.getOrders()){
            if(o.getOrderID().equals(orderID)) return CISConstants.DUP_ORDER_ERR;
        }

        for (CISUser u: users){
            for (Order o: u.getOrders()){
                if(o.getOrderID().equals(orderID)) return CISConstants.DUP_ORDER_ERR;
            }
        }

        MenuItem item = null;

        for (MenuItem m: menu.getEatriumItems()){
            if(m.getId().equals(menuItemID)){
                item = m;
            }
        }

        if(item==null) return CISConstants.INVALID_MENU_ITEM_ERR;

        if(item.getAmountAvailable()==0) return CISConstants.SOLD_OUT_ERR;

        if(item.getPrice()>user.getMoney()) return CISConstants.USER_BROKE_ERR;

        ArrayList<Order> newOrders = user.getOrders();
        newOrders.add(new Order(menuItemID, orderType, orderID));
        user.setOrders(newOrders);
        user.setMoney(user.getMoney()-item.getPrice());

        item.setAmountAvailable(item.getAmountAvailable()-1);


        return CISConstants.SUCCESS;
    }

    public String deleteOrder(Request req){
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        String userID = req.getParam(CISConstants.USER_ID_PARAM);

        if(userID==null||orderID==null) return CISConstants.PARAM_MISSING_ERR;
        CISUser user = null;
        for (CISUser u: users){
            if(u.getUserID().equals(userID)) user = u;
        }
        if(user==null) return CISConstants.USER_INVALID_ERR;
        boolean orderExists = false;
        for(Order o: user.getOrders()){
            if(o.getOrderID().equals(orderID)){
                orderExists = true;
                ArrayList<Order> newOrder = user.getOrders();
                newOrder.remove(o);
                break;
            }
        }
        if(!orderExists) return CISConstants.ORDER_INVALID_ERR;
        return CISConstants.SUCCESS;
    }

    public String getOrder(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        String orderID = req.getParam(CISConstants.ORDER_ID_PARAM);
        if(userID==null||orderID==null) return CISConstants.PARAM_MISSING_ERR;
        CISUser user = null;
        for (CISUser u:users){
            if(userID.equals(u.getUserID())){
                user = u;
                break;
            }
        }
        Order order = null;
        if(user==null) return CISConstants.USER_INVALID_ERR;
        for (Order o:user.getOrders()){
            if(o.getOrderID().equals(orderID)){
                order = o;
            }
        }
        if(order==null) return CISConstants.ORDER_INVALID_ERR;


        return "Order{itemID='"+order.getItemID()+"', type='"+order.getType()+"', orderID='"+orderID+"'}";
    }

    public String getItem(Request req){
        String itemID = req.getParam(CISConstants.ITEM_ID_PARAM);
        if(itemID==null)return CISConstants.PARAM_MISSING_ERR;
        MenuItem item = null;
        for (MenuItem m: menu.getEatriumItems()){
            if(itemID.equals(m.getId())){
                item = m;
            }
        }
        if(item==null) return CISConstants.INVALID_MENU_ITEM_ERR;

        return "MenuItem{name='"+ item.getName()+"', " +
                "description='" + item.getDescription() +
                "', price=" + item.getPrice() + ", id='" + item.getId() +"', " +
                "amountAvailable=" + item.getAmountAvailable() + ", type='" + item.getType() + "'}";
    }

    public String getCart(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        CISUser user = null;
        if(userID==null) return CISConstants.PARAM_MISSING_ERR;
        for (CISUser u : users){
            if(userID.equals(u.getUserID())){
                user = u;
                break;
            }
        }
        if(user==null)return CISConstants.USER_INVALID_ERR;

        String ans = "";
        for (Order order:user.getOrders()){
            ans+="Order{itemID='"+order.getItemID()+"', type='"+order.getType()+"', orderID='"+order.getOrderID()+"'}, ";
        }

        return ans;
    }

    public String getUser(Request req){
        String userID = req.getParam(CISConstants.USER_ID_PARAM);
        CISUser user = null;
        if(userID==null) return CISConstants.PARAM_MISSING_ERR;
        for (CISUser u : users){
            if(userID.equals(u.getUserID())){
                user = u;
                break;
            }
        }
        if(user==null)return CISConstants.USER_INVALID_ERR;

        String ans = "CISUser{userID='" + user.getUserID() +"', name='" + user.getName() + "', " +
                "yearLevel='" + user.getYearLevel() + "', orders= ";
        for (Order order:user.getOrders()){
            ans+="Order{itemID='"+order.getItemID()+"', type='"+order.getType()+"', orderID='"+order.getOrderID()+"'}, ";
        }
        ans+= "money=" + user.getMoney() + "}";


        return ans;
    }

    public static void main(String[] args)
    {
        CIServer f = new CIServer();
        f.start(args);
    }
}
