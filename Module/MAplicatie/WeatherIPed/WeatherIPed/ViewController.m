//
//  ViewController.m
//  WeatherIPed
//
//  Created by Vlad Minea on 21/04/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "ViewController.h"
#import <MapKit/MapKit.h>
#import "LocationManager.h"
#import "WeatherInfoViewController.h"

@interface ViewController () <MKMapViewDelegate, CLLocationManagerDelegate>
{
    IBOutlet MKMapView *_mapView;
    IBOutlet UIView *_headerView;
    IBOutlet UIButton *_findAboutWeatherButton;
    IBOutlet UIButton *_findAboutPopulationButton;
    
    IBOutlet UIView *_textInputView;
    IBOutlet UITextField *_textField;
    IBOutlet UIButton *_goBtn;
    
    MKPointAnnotation *_myLocationPoint;
    NSMutableArray *_annotations;
    
    BOOL _didAskForWeather;
}
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    [self setupCoreLocation];
}

- (void) viewWillAppear:(BOOL) animated
{
    [super viewWillAppear:animated];
    [self setupLayout];
    
    [self showWeather];
}

- (void) viewWillDisappear:(BOOL) animated
{
    [super viewWillDisappear:animated];
    self.title = nil;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void) setupLayout
{
    [_headerView setBackgroundColor:[UIColor greenColor]];
    self.title = _cityName;
    [self setupMap];
}

- (void) setupCoreLocation
{
//    [_LM requestLocation];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(locationManagerDidChangeAuthorizationStatus:)
                                                 name:@"locationManagerDidChangeAuthorizationStatus"
                                               object:nil];
    
    if (![_LM amIAuthorized])
        [_LM requestAuthorization];
}

- (void) setupMap
{
    _mapView.delegate = self;
    
//    [_mapView setShowsUserLocation:YES];
//    [_mapView showsUserLocation];
}

#pragma mark - MapView Delegate Methods

-(void)setupMapWithLocations:(NSArray *)locations
{
    _annotations = [NSMutableArray new];
    
    for (CLLocation *aLocation in locations)
    {
        MKPointAnnotation *annotation = [[MKPointAnnotation alloc] init];
        annotation.coordinate = aLocation.coordinate;
        annotation.title = @"You're here";
        NSLog(@"%f %f", aLocation.coordinate.latitude , aLocation.coordinate.longitude);
        
        [_annotations addObject:annotation];
    }
    
    [_mapView removeAnnotations:_mapView.annotations];
    [_mapView addAnnotations:_annotations];
    
    CLLocation *myLocation = locations.firstObject;
    
    MKCoordinateRegion region = MKCoordinateRegionMake(myLocation.coordinate, MKCoordinateSpanMake(0.05, 0.05));
    [_mapView setRegion:region];
    
    
    NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
    
    [dictionary setObject:kCodeInputCoordinates forKey:@"codeinput"];
    [dictionary setObject:kCodeOutputWeather forKey:@"codeoutput"];
    
    NSArray *lat = [self getDegreesMinutesSecondsFromCoordinate:myLocation.coordinate.latitude];
    NSArray *lng = [self getDegreesMinutesSecondsFromCoordinate:myLocation.coordinate.longitude];
    NSMutableDictionary *dataDict = [[NSMutableDictionary alloc] init];
    [dataDict setObject:lat[0]
                 forKey:@"lat_gr"];
    [dataDict setObject:lat[1]
                 forKey:@"lat_min"];
    [dataDict setObject:lat[2]
                 forKey:@"lat_sec"];
    [dataDict setObject:lng[0]
                 forKey:@"long_gr"];
    [dataDict setObject:lng[1]
                 forKey:@"long_min"];
    [dataDict setObject:lng[2]
                 forKey:@"long_sec"];
    
    [dictionary setObject:dataDict forKey:@"data"];
    
    __weak typeof(self) _weakSelf = self;
    
    [_MT sendMessage:dictionary
     completionBlock:^(NSDictionary * responseDict){
         
         _weakSelf.weatherInfo = responseDict;
         [_weakSelf showWeather];
         
     }];
}

//-(MKAnnotationView *)mapView:(MKMapView *)mapView viewForAnnotation:(id<MKAnnotation>)annotation
//{
//    if ([annotation isKindOfClass:[MKUserLocation class]])
//        return nil;
//    
//    NSInteger index = [_annotations indexOfObject:annotation];
//    
//    MKAnnotationView *annotationView = [mapView dequeueReusableAnnotationViewWithIdentifier:@"ReuseAnotation"];
//    
//    if (!annotationView)
//    {
//        annotationView = [[MKAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:@"ReuseAnotation"];
//        
//        annotationView.canShowCallout = NO;
//        annotationView.enabled = YES;
//        annotationView.highlighted = YES;
//        annotationView.draggable = NO;
//    }
//    else
//        annotationView.annotation = annotation;
//    
//    [annotationView setBackgroundColor:[UIColor blueColor]];
//    
//    
//    return annotationView;
//}

-(void)mapView:(MKMapView *)mapView didSelectAnnotationView:(MKAnnotationView *)view
{
    [mapView deselectAnnotation:view.annotation animated:YES];
    [self showWeather];
//    NSInteger index = [_annotations indexOfObject:view.annotation];
    
}

#pragma mark - IBActions

- (IBAction) didPressFindAbout:(id) sender
{
    NSString *message = @"Please select a way provide us a location to find information about it's ";
    
    if ([sender tag] == 11)
    {
        _didAskForWeather = YES;
        message = [message stringByAppendingString:@"weather."];
    }
    else
    {
        _didAskForWeather = NO;
        message = [message stringByAppendingString:@"population."];
    }
    
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Location Options"
                                                                             message:message
                                                                      preferredStyle:UIAlertControllerStyleAlert];
    
    __weak typeof(self) _weakSelf = self;
    
    UIAlertAction *deviceLocationAction = [UIAlertAction actionWithTitle:@"My Location"
                                                                   style:UIAlertActionStyleDefault
                                                                 handler:^(UIAlertAction * _Nonnull action) {
                                                                     
                                                                     [[NSNotificationCenter defaultCenter] addObserver:_weakSelf
                                                                                                              selector:@selector(gotUserLocation:)
                                                                                                                  name:@"didFindLocation"
                                                                                                                object:nil];
                                                                     [_LM requestLocation];
                                                                 }];
    
    UIAlertAction *specificLocationAction = [UIAlertAction actionWithTitle:@"Specific Location"
                                                                     style:UIAlertActionStyleDefault
                                                                   handler:^(UIAlertAction * _Nonnull action) {
                                                                       //present input
                                                                       [_weakSelf presentInput:YES];
                                                                   }];
    
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancel"
                                                           style:UIAlertActionStyleCancel
                                                         handler:^(UIAlertAction * _Nonnull action) {
//                                                             [alertController dismissViewControllerAnimated: completion:<#^(void)completion#>]
                                                         }];
    
    [alertController addAction:deviceLocationAction];
    [alertController addAction:specificLocationAction];
    [alertController addAction:cancelAction];
    
    [self presentViewController:alertController animated:YES completion:nil];
}

- (IBAction) didPressGo: (id) sender
{
    [self presentInput:NO];
}

#pragma mark - LocationManager Methods & Delegate Methods

-(void)gotUserLocation:(NSNotification *) notification
{
    [[NSNotificationCenter defaultCenter] removeObserver:self name:@"didFindLocation" object:nil];
    
    CLLocation *userLocation = notification.object;
    [self setupMapWithLocations:@[userLocation]];
}

- (void) locationManagerDidChangeAuthorizationStatus:(NSNotification *) notification
{
    //
}

#pragma mark - Utils

- (void) showWeather
{
    if (_weatherInfo && !self.presentedViewController)
    {
        //        {"code":1101,"data":{"precipitation":"no","skyState":"few clouds","tMax":23,"temperature":21,"humidity":"64","tMin":20,"pressure":"1005","windDirection":"East-southeast"},"message":"ok"}
        //present weather;
        
        //success
        //show weather info
        WeatherInfoViewController *vc = [[UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]] instantiateViewControllerWithIdentifier:@"weatherInfoVC"];
        
        [self presentViewController:vc animated:YES completion:^{
            [vc setupWithWeatherInfo:_weatherInfo];
        }];
    }
//    else
//        _weatherInfo = nil;
}

- (NSArray *) getDegreesMinutesSecondsFromCoordinate:(CLLocationDegrees) coordinate
{
    int degrees = (int) coordinate;
    double minutesAsDouble = ((double)coordinate - degrees) * 60;
    int minutesAsInt = (int) minutesAsDouble;
    
    int seconds = (minutesAsDouble - minutesAsInt) * 60;
    
    if (minutesAsInt < 0)
        minutesAsInt *= -1;
    if (seconds < 0)
        seconds *= -1;
    
    return [NSArray arrayWithObjects:[NSNumber numberWithInt:degrees], [NSNumber numberWithInt:minutesAsInt], [NSNumber numberWithInt:seconds], nil];
}

- (void) presentInput:(BOOL) present
{
    CGRect frame = _textInputView.frame;
    
    
    if (frame.origin.y < 44)
        present = !present;
    
    [UIView animateWithDuration:0.5
                     animations:^{
                        
                         if (!present)
                             [_textInputView setFrame:CGRectMake(frame.origin.x, frame.origin.y - frame.size.height, frame.size.width, frame.size.height)];
                         else
                             [_textInputView setFrame:CGRectMake(frame.origin.x, frame.origin.y + frame.size.height, frame.size.width, frame.size.height)];
                     }];
}

@end
