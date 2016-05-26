//
//  WeatherInfoViewController.h
//  WeatherIPed
//
//  Created by Vlad Minea on 24/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface WeatherInfoViewController : UIViewController

+ (WeatherInfoViewController *) presentWeatherInfoViewControllerWithWeatherInfo:(NSDictionary *) weatherInfo;
- (void) setupWithWeatherInfo:(NSDictionary *) weatherInfo;

@end
