//
//  MessageTransmitter.h
//  WeatherIPed
//
//  Created by Vlad Minea on 26/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MessageTransmitter : NSObject

+ (instancetype) sharedInstance;

- (void) sendMessage:(NSDictionary *) messageDictionary completionBlock:(void (^)(NSDictionary *)) completionBlock;

@end

@compatibility_alias MT MessageTransmitter;
#define _MT [MessageTransmitter sharedInstance]