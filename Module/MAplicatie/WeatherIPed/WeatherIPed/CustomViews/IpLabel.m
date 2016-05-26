//
//  IpLabel.m
//  WeatherIPed
//
//  Created by Vlad Minea on 13/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "IpLabel.h"

@implementation IpLabel

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/

- (instancetype) init
{
    if (self = [super init])
    {
        //do some init stuff
        
        self.textColor = [UIColor whiteColor];
    }
    
    return self;
}

@end
