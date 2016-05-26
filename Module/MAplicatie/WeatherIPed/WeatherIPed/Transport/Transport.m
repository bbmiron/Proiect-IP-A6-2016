//
//  Transport.m
//  WeatherIPed
//
//  Created by Vlad Minea on 27/04/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "Transport.h"

@interface Transport() <NSStreamDelegate>
{
    CFReadStreamRef _readStreamRef;
    CFWriteStreamRef _writeStreamRef;
    NSInputStream *_inputStream;
    NSOutputStream *_outputStream;
    
//    returnType (^blockName)(parameterTypes) = ^returnType(parameters) {...};
//    (NSDictionary * (^)(NSDictionary *)) completionBlock
    void (^_mainCompletionBlock)(id);
}
@end

@implementation Transport

#pragma mark - Singleton and Connect/Disconnect

+ (instancetype) sharedInstance
{
    static Transport *_sharedInstance = nil;
    dispatch_once_t onceToken = 0;
    _dispatch_once(&onceToken, ^{
        _sharedInstance = [[Transport alloc] init];
        
        
    });
    
    return _sharedInstance;
}

/*
 {"errorcode" :"500", "message":"Codul de input sau output este gresit","data":{"temperature":"23","tMin":"20","tMax":"25","humidity":"23%","pressure":"12%","windDirection":"N",skyState:"as"}}
 */

- (BOOL) connect
{
    if (_readStreamRef || _writeStreamRef)
        return NO;
    
    CFStreamCreatePairWithSocketToHost(NULL, (__bridge CFStringRef)kHostIp, kHostPort, &_readStreamRef, &_writeStreamRef);
    
    _inputStream = (__bridge NSInputStream *) _readStreamRef;
    [_inputStream setDelegate:self];
    [_inputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    [_inputStream open];
    
    _outputStream = (__bridge NSOutputStream *) _writeStreamRef;
    [_outputStream setDelegate:self];
    [_outputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
    [_outputStream open];
    
    return YES;
}

- (void)disconnect
{
    if(_readStreamRef) {
        [_inputStream close];
        [_inputStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        _inputStream = nil;
        
        CFRelease(_readStreamRef);
        _readStreamRef = NULL;
    }
    
    if(_writeStreamRef) {
        [_outputStream close];
        [_outputStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
        _outputStream = nil;
        
        CFRelease(_writeStreamRef);
        _writeStreamRef = NULL;
    }
}

#pragma mark - NSStreamDelegate

- (void) stream:(NSStream *) aStream handleEvent:(NSStreamEvent) eventCode
{
    switch (eventCode) {
        case NSStreamEventNone:
            f__logObject(@"--NSStreamEventNone")
            break;
        case NSStreamEventOpenCompleted:
            if ([aStream isEqual:_outputStream])
                f__logObject(@"--client has connected");
            f__logObject(@"--NSStreamEventOpenCompleted")
            break;
        case NSStreamEventHasBytesAvailable:
            //read
            if ([aStream isEqual:_inputStream])
                [self readBytes];
            
            f__logObject(@"--NSStreamEventHasBytesAvailable")
            break;
        case NSStreamEventHasSpaceAvailable:
            //write
            f__logObject(@"--NSStreamEventHasSpaceAvailable")
            break;
        case NSStreamEventErrorOccurred:
            //error
            f__logObject(@"--NSStreamEventErrorOccurred")
            break;
        case NSStreamEventEndEncountered:
            //close connection
            f__logObject(@"--NSStreamEventEndEncountered")
            break;
        default:
            break;
    }
    
    NSLog(@"event code : %lu",(NSStreamEvent) (unsigned long)eventCode);
}

#pragma mark - Read/Write Bytes

- (void) readBytes
{
    uint8_t buffer[1024];
    NSInteger readBytes = 0;
    
    while ([_inputStream hasBytesAvailable]) {
        memset(buffer, 0, sizeof(buffer));
        
        readBytes = [_inputStream read:buffer maxLength:sizeof(buffer)];
        if (readBytes > 0) {
            NSString *outputString = [[NSString alloc] initWithBytes:buffer
                                                              length:readBytes
                                                            encoding:NSUTF8StringEncoding];
            
            if (outputString) {
                NSLog(@"Server: %@", outputString);
                
                NSDictionary *dict = [self dictFromJsonString:outputString];
                
                if (_mainCompletionBlock)
                    _mainCompletionBlock(dict);
            }
        }
    }
}

- (void) sendMessage:(NSString *) message completionBlock:(void (^)(id responseObject)) completionBlock
{
    if (completionBlock)
        _mainCompletionBlock = completionBlock;
    //    //with city
    NSDictionary *dict = @{
                           @"codeinput" : @"14",
                           @"codeoutput" : @"12",
                           @"data" : @{
                                   @"city" : @"iasi",
                                   @"district" : @"copou"
                                   }
                           };
//    //with coordinates
    dict =  @{@"codeinput":@"13",
              @"codeoutput":@"15",
              @"data":@{
                        @"long_min":@"9",
                        @"lat_gr":@"27",
                        @"lat_min":@"35",
                        @"lat_sec":@"20",
                        @"long_gr":@"47",
                        @"long_sec":@"44"}
              };
    
//    message = [self jsonDataFromDict:dict];
    
    
    message = [message stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    message = [message stringByReplacingOccurrencesOfString:@" " withString:@""];
    
    
    message = [message stringByAppendingString:@"\n"];
    NSData *outputData = [[NSData alloc] initWithData:[message dataUsingEncoding:NSUTF8StringEncoding]];
    
    NSLog(@"%ld", (long)outputData.length);
    NSLog(@"\nmessage:\n%@", message);
    
//    NSDictionary *dict = [self dictFromJsonString:outputString];
    dict = @{
                @"code" :@"1101",
                @"message":@"Codul de input sau output este gresit",
                @"data":@{
                            @"temperature":@"23",
                            @"tMin":@"20",
                            @"tMax":@"25",
                            @"humidity":@"23%",
                            @"pressure":@"12%",
                            @"windDirection":@"N",
                            @"skyState":@"as"}};
    
    if (_mainCompletionBlock)
        _mainCompletionBlock(dict);
    
//    [_outputStream write:[outputData bytes] maxLength:[outputData length]];
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
