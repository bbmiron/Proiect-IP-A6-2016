//
//  Transport.h
//  WeatherIPed
//
//  Created by Vlad Minea on 27/04/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol TransportDelegate;

@interface Transport : NSObject

@property (nonatomic, weak) id <TransportDelegate> delegate;

+ (instancetype) sharedInstance;

- (BOOL) connect;

- (void) sendMessage:(NSString *) message completionBlock:(void (^)(id responseObject)) completionBlock;;

@end

@protocol TransportDelegate <NSObject>

- (void) transportDidConnect;
- (void) transportDidDisconnect;
- (void) transportDidReceiveMessage;

@end

@compatibility_alias T Transport;
#define _T [Transport sharedInstance]