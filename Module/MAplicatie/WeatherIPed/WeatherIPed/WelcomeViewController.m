//
//  WelcomeViewController.m
//  WeatherIPed
//
//  Created by Vlad Minea on 22/04/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "WelcomeViewController.h"
#import "ViewController.h"

#import "Transport.h"

@interface WelcomeViewController ()
{
    IBOutlet UITextField *_cityTextField;
    
    Transport *_transport;
}
@end

@implementation WelcomeViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    _transport = [Transport sharedInstance];
}

- (void) viewWillAppear:(BOOL) animated
{
    [self setupLayout];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) setupLayout
{
    self.title = @"Welcome";
}

#pragma mark - IBActions

- (IBAction) didPressCheckWeather:(id) sender
{
    if (_cityTextField.text.length)
    {
        //show spinner
        //call api
        //go to map
        
        NSString *cityName = _cityTextField.text;
        NSMutableDictionary *params = [[NSMutableDictionary alloc ] init];
        
        [params setObject:@"14" forKey:@"codeinput"];
        [params setObject:@"12" forKey:@"codeoutput"];
        
        [params setObject:@{ @"city" : cityName,
                             @"district" : @"copou"}
                   forKey:@"data"];
        
//        params = @{@"codeinput":@"13",
//          @"codeoutput":@"12",
//          @"data":@{
//                  @"long_min":@9,
//                  @"lat_gr":@27,
//                  @"lat_min":@35,
//                  @"lat_sec":@20,
//                  @"long_gr":@47,
//                  @"long_sec":@44}
//          }.mutableCopy;
        
        __weak typeof(self) _weakSelf = self;
        
        [_MT sendMessage:params completionBlock:^void (NSDictionary *responseDict) {
                       if (responseDict)
                           [_weakSelf performSegueWithIdentifier:@"goToMap" sender:responseDict];
                   }];
        
    }
    else
    {
        //show alert
        UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Warning!"
                                                                                 message:@"You did not enter a city name. Do you want to?"
                                                                          preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *tryAgainAction = [UIAlertAction actionWithTitle:@"Try again"
                                                                 style:UIAlertActionStyleDefault
                                                               handler:^(UIAlertAction * _Nonnull action) {
                                                                   [alertController dismissViewControllerAnimated:YES completion:nil];
                                                               }];
        UIAlertAction *skipAction = [UIAlertAction actionWithTitle:@"Skip to map"
                                                             style:UIAlertActionStyleDefault
                                                           handler:^(UIAlertAction * _Nonnull action) {
                                                               [self performSegueWithIdentifier:@"goToMap" sender:nil];
                                                           }];
        
        [alertController addAction:tryAgainAction];
        [alertController addAction:skipAction];
        
        [self presentViewController:alertController animated:YES completion:nil];
    }
}

- (IBAction) didPressSkip:(id) sender
{
    //go directly to map
    [self performSegueWithIdentifier:@"goToMap" sender:nil];
}

- (IBAction) didPressConnect:(id) sender
{
    if ([_transport connect]){
        NSLog(@"tryed to connect");
    }
    else {
//        [_transport sendMessage:@"test\n"];   
    }
}


#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
    if (sender)
    {
        NSString *cityName = _cityTextField.text;
//        NSMutableDictionary *params = [[NSMutableDictionary alloc ] init];
//        
//        [params setObject:@"14" forKey:@"codeinput"];
//        [params setObject:@"12" forKey:@"codeoutput"];
//        
//        [params setObject:@{ @"city" : cityName,
//                             @"district" : @"copou"}
//                   forKey:@"data"];
//        
//        [_transport sendDictionary:params
//                   completionBlock:^void (NSDictionary *responseDict) {
//                       
//                       if (responseDict)
//                       {
        NSDictionary *responseDict = sender;
                           ViewController *destination = segue.destinationViewController;
                           destination.cityName = cityName;
                           destination.weatherInfo = responseDict;
//                       }
//                       
//                   }];
        
    }
    
    self.title = nil;
}


@end
