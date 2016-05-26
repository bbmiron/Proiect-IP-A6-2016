//
//  FunkyDefines.h
//  WeatherIPed
//
//  Created by Vlad Minea on 27/04/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#ifndef FunkyDefines_h
#define FunkyDefines_h

#pragma mark - Conversions

#define div255(x) ((x)/255.0f)

#pragma mark - Object Creators

#define f__string(...) [NSString stringWithFormat:__VA_ARGS__,nil]
#define f__indexPath(section,row) [NSIndexPath indexPathForRow:row inSection:section]
#define f__imageNamed(...) [UIImage imageNamed:f__string((__VA_ARGS__))]
#define f__color(r, g, b, a)    [UIColor colorWithRed:div255(r) green:div255(g) blue:div255(b) alpha:div255(a)]

#pragma mark - NSLogs

#define f__logObject(object) NSLog(@"%s = <%@>", #object, object);


#endif /* FunkyDefines_h */
