package by.bsu.fpmi.up.webchatapp.servlets;

import by.bsu.fpmi.up.webchatapp.Constants;
import by.bsu.fpmi.up.webchatapp.InvalidTokenException;
import by.bsu.fpmi.up.webchatapp.MessageExistException;
import by.bsu.fpmi.up.webchatapp.models.Message;
import by.bsu.fpmi.up.webchatapp.storages.InMemoryMessageStorage;
import by.bsu.fpmi.up.webchatapp.storages.MessageStorage;
import by.bsu.fpmi.up.webchatapp.storages.Portion;
import by.bsu.fpmi.up.webchatapp.utils.MessageHelper;
import by.bsu.fpmi.up.webchatapp.utils.StringUtils;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/chat")
public class ChatServlet extends HttpServlet {
    private MessageStorage messageStorage = new InMemoryMessageStorage();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter(Constants.REQUEST_PARAM_TOKEN);
        if (StringUtils.isEmpty(token)) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Token query parameter is required");
        }
        try {
            int index = MessageHelper.parseToken(token);
            if (index > messageStorage.size()) {
                resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST,
                        String.format("Incorrect token in request: %s. Server does not have so many messages", token));
            }
            Portion portion = new Portion(index);
            List<Message> messages = messageStorage.getPortion(portion);
            String responseBody = MessageHelper.buildServerResponseBody(messages, messageStorage.size());
            resp.getOutputStream().println(responseBody);
        } catch (InvalidTokenException e) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getClientMessage(req.getInputStream());
            messageStorage.addMessage(message);
        } catch (ParseException e) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Incorrect request body");
        } catch (MessageExistException e){
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Message with same id exist");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Message message = MessageHelper.getClientMessage(req.getInputStream());
            if (!messageStorage.updateMessage(message)) {
                resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "This message does not exist");
            }
        } catch (ParseException e) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST,  "Incorrect request body");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String messageId = req.getParameter(Constants.REQUEST_PARAM_MESSAGE_ID);
        if (StringUtils.isEmpty(messageId)) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "Message id query parameter is required");
        }
        if (!messageStorage.removeMessage(messageId)) {
            resp.sendError(Constants.RESPONSE_CODE_BAD_REQUEST, "This message does not exist");
        }
    }

}
