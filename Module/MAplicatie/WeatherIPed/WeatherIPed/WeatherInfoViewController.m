//
//  WeatherInfoViewController.m
//  WeatherIPed
//
//  Created by Vlad Minea on 24/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "WeatherInfoViewController.h"

@interface WeatherInfoViewController ()
{
    IBOutlet UILabel *_skyState;
    IBOutlet UILabel *_temperature;
    IBOutlet UILabel *_tMax;
    IBOutlet UILabel *_tMin;
    IBOutlet UILabel *_humidity;
    IBOutlet UILabel *_pressure;
    IBOutlet UILabel *_windDirection;
    IBOutlet UILabel *_precipitation;
    
    IBOutlet UILabel *_cityName;
    
}
@end

@implementation WeatherInfoViewController

+ (WeatherInfoViewController *) presentWeatherInfoViewControllerWithWeatherInfo:(NSDictionary *) weatherInfo
{
    WeatherInfoViewController *vc = [[WeatherInfoViewController alloc] init];
    
//    vc.modalPresentationStyle = UIModalPresentationCurrentContext;
//    vc.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
    
    [vc setupWithWeatherInfo:weatherInfo];
    
    return vc;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    CAGradientLayer *gradient = [CAGradientLayer layer];
    
    gradient.frame = self.view.bounds;
    gradient.colors = [NSArray arrayWithObjects:(id)[[UIColor whiteColor] CGColor], (id)[f__color(2, 31, 69, 1) CGColor], nil];
    [self.view.layer insertSublayer:gradient atIndex:0];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) setupWithWeatherInfo:(NSDictionary *) weatherInfo
{
    _skyState.text = weatherInfo[@"skyState"];
    _temperature.text = f__string(@"%@",weatherInfo[@"temperature"]);
    _tMax.text = f__string(@"%@", weatherInfo[@"tMax"]);
    _tMin.text = f__string(@"%@", weatherInfo[@"tMin"]);
    _humidity.text = f__string(@"%@", weatherInfo[@"humidity"]);
    _pressure.text = f__string(@"%@", weatherInfo[@"pressure"]);
    _windDirection.text = f__string(@"%@", weatherInfo[@"windDirection"]);
    _precipitation.text = f__string(@"%@", weatherInfo[@"pressure"]);
}


- (IBAction)didPressClode:(id)sender
{
    [self dismissViewControllerAnimated:YES completion:nil];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
