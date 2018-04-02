//
//  ViewController.m
//  WebsocketStompKit
//
//  Created by Miaoz on 2017/11/15.
//  Copyright © 2017年 Miaoz. All rights reserved.
//

#import "ViewController.h"
//#import "WebsocketStompKit/WebsocketStompKit.h"
#import "WebsocketStompKit.h"
@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.

    NSURL *websocketUrl = [NSURL URLWithString:@"http://172.18.19.97:8088/gs-guide-websocket/websocket"];//
    STOMPClient *client = [[STOMPClient alloc] initWithURL:websocketUrl webSocketHeaders:@{} useHeartbeat:YES];
    // connect to the broker
    [client connectWithLogin:@""
                    passcode:@""
           completionHandler:^(STOMPFrame *_, NSError *error) {
               if (error) {
                   NSLog(@"%@", error);
                   return;
               }
               
//               [client subscribeTo:@"/app/sub" messageHandler:^(STOMPMessage *message) {
//                    NSLog(@"/app/sub --- %@",message.body);
//               }];
//
//               [client subscribeTo:@"/queue/1/message" messageHandler:^(STOMPMessage *message) {
//                    NSLog(@"/queue/1/message --- %@",message.body);
//               }];
               // send a message
//               [client sendTo:@"/queue/myqueue" body:@"Hello, iOS!"];
               // and disconnect
//               [client disconnect];
           }];
    
       [client subscribeTo:@"/app/sub" messageHandler:^(STOMPMessage *message) {
            NSLog(@"/app/sub --- %@",message.body);
       }];

       [client subscribeTo:@"/queue/1/message" messageHandler:^(STOMPMessage *message) {
            NSLog(@"/queue/1/message --- %@",message.body);
       }];

    // and disconnect
//      [client disconnect];
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end
