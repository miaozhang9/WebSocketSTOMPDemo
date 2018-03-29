//
//  ViewController.swift
//  StompClientLib
//
//  Created by wrathchaos on 07/07/2017.
//  Copyright (c) 2017 wrathchaos. All rights reserved.
//

import UIKit
import StompClientLib

class ViewController: UIViewController, StompClientLibDelegate {
    
    var socketClient = StompClientLib()
    var titleLab = UILabel();
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let button = UIButton(frame:CGRect(x:10, y:150, width:100, height:30))
        button.setTitle("发送", for:.normal) //普通状态下的文字
        button.setTitleColor(UIColor.black, for: .normal)
        button.titleLabel?.textColor = UIColor.black
        button.titleLabel?.font = UIFont.systemFont(ofSize: 11)
        button.backgroundColor = UIColor.yellow
//        button.addTarget(self, action: #selector(click(_:)), for: .touchUpInside)
        button.addTarget(self, action:#selector(send(button:)), for:.touchUpInside)
       self.view.addSubview(button)
        
        let button2 = UIButton(frame:CGRect(x:10, y:250, width:100, height:30))
        button2.setTitle("注册", for:.normal) //普通状态下的文字
        button2.setTitleColor(UIColor.black, for: .normal)
        button2.titleLabel?.font = UIFont.systemFont(ofSize: 11)
        button2.backgroundColor = UIColor.yellow
        //        button.addTarget(self, action: #selector(click(_:)), for: .touchUpInside)
        button2.addTarget(self, action:#selector(register(button:)), for:.touchUpInside)
        self.view.addSubview(button2)
        
        titleLab.frame = CGRect(x:10, y:450, width:300, height:30);
        titleLab.backgroundColor = UIColor.red
        self.view.addSubview(titleLab)
        // Connection with socket
//        registerSocket()
    }
    
    //下
    @objc func send(button:UIButton) {
        sendMessage()
    }
   
    @objc func register(button:UIButton) {
         registerSocket()
    }
    
    
    func registerSocket(){
        let baseURL = "http://your-url-is-here/"
        // Cut the first 7 character which are "http://" Not necessary!!!
        // substring is depracated in iOS 11, use prefix instead :)
        // let wsURL = baseURL.substring(from:baseURL.index(baseURL.startIndex, offsetBy: 7))
        let wsURL = baseURL.prefix(7)
        var completedWSURL = "ws://\(wsURL)gateway/websocket"
//        print("Completed WS URL : \(completedWSURL)")
        completedWSURL = "http://172.18.19.97:8088/gs-guide-websocket/websocket";
        print("Completed WS URL : \(completedWSURL)")
        let url = NSURL(string: completedWSURL)!
        
        socketClient.openSocketWithURLRequest(request: NSURLRequest(url: url as URL) , delegate: self as StompClientLibDelegate)
       
    }
    
    func sendMessage()  {
        socketClient.sendMessage(message: "/app/message", toDestination: "greetings", withHeaders: [StompCommands.commandHeaderContentType:"application/json;charset=UTF-8"], withReceipt: nil);
    }
    
    func stompClientDidConnect(client: StompClientLib!) {
//        let topic = "/topic/your topic is here/"
        let topic = "/app/sub"
        print("Socket is Connected : \(topic)")
        socketClient.subscribe(destination: topic)
        
    }
    
    func stompClientDidDisconnect(client: StompClientLib!) {
        print("Socket is Disconnected")
        registerSocket()
    }
    
    func stompClientWillDisconnect(client: StompClientLib!, withError error: NSError) {
         print("Socket is WillDisconnect")
    }
    
    func stompClient(client: StompClientLib!, didReceiveMessageWithJSONBody jsonBody: AnyObject?, withHeader header: [String : String]?, withDestination destination: String) {
        print("DESTIONATION : \(destination)")
        print("JSON BODY : \(String(describing: jsonBody))")
        var dic:Dictionary<String,String> = jsonBody as! Dictionary<String, String>;
        let content = dic["content"]
     
        titleLab.text = content;
//            String(describing: jsonBody)
        
    }
    
    func stompClientJSONBody(client: StompClientLib!, didReceiveMessageWithJSONBody jsonBody: String?, withHeader header: [String : String]?, withDestination destination: String) {
        print("DESTIONATION : \(destination)")
        print("String JSON BODY : \(String(describing: jsonBody))")
        titleLab.text = String(describing: jsonBody);
    }
    
    func serverDidSendReceipt(client: StompClientLib!, withReceiptId receiptId: String) {
        print("Receipt : \(receiptId)")
    }
    
    func serverDidSendError(client: StompClientLib!, withErrorMessage description: String, detailedErrorMessage message: String?) {
        print("Error : \(String(describing: message))")
    }
    
    func serverDidSendPing() {
        print("Server Ping")
    }

}
