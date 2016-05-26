//
//  MessageTransmitter.m
//  WeatherIPed
//
//  Created by Vlad Minea on 26/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "MessageTransmitter.h"

@implementation MessageTransmitter

+ (instancetype) sharedInstance
{
    static MessageTransmitter *_sharedInstance = nil;
    dispatch_once_t onceToken = 0;
    _dispatch_once(&onceToken, ^{
        _sharedInstance = [[MessageTransmitter alloc] init];
        
    });
    
    return _sharedInstance;
}

- (void) sendMessage:(NSDictionary *) messageDictionary completionBlock:(void (^)(NSDictionary *)) completionBlock
{
    if (messageDictionary)
    {
        NSString *message = [self jsonDataFromDict:messageDictionary];
        NSLog(@"\njson:\n%@", message);
        
        [_T sendMessage:message completionBlock:^(id responseObject){
            
            if ([responseObject isKindOfClass:[NSDictionary class]])
            {
                NSDictionary *responseDict = responseObject;
                
                NSNumber *code = responseDict[@"code"];
                
                NSDictionary *data = responseDict[@"data"];
                NSString *errorMessage = responseDict[@"message"];
                
                if (code.intValue / 1000 == 1)
                {
                    //success
                    
                    completionBlock(data);
                }
                else
                {
                    //error
                    //show error with message
                    
                    completionBlock(nil);
                }
            }
            
        }];
    }
}

#pragma mark - JSON Serialization/Deserialization

- (NSString *) jsonDataFromDict:(NSDictionary *) dictionary
{
    NSError *jsonError;
    NSData *data = [NSJSONSerialization dataWithJSONObject:dictionary
                                                   options:NSJSONWritingPrettyPrinted
                                                     error:&jsonError];
    NSString *message = [[NSString alloc] initWithBytes:[data bytes]
                                                 length:[data length]
                                               encoding:NSUTF8StringEncoding];
    
    return message;
}

- (NSDictionary *) dictFromJsonString:(NSString *) json
{
    NSError *jsonError;
    NSData *data = [json dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dictionary = [NSJSONSerialization JSONObjectWithData:data
                                                               options:NSJSONReadingMutableContainers
                                                                 error:&jsonError];
    
    return dictionary;
}
@end
