//
//  LocationManager.m
//  WeatherIPed
//
//  Created by Vlad Minea on 19/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import "LocationManager.h"
@interface LocationManager()

@end

@implementation LocationManager

+(LocationManager *)sharedInstance
{
    static LocationManager *sharedLocationManager;
    
    if (sharedLocationManager == nil)
    {
        static dispatch_once_t onceToken;
        dispatch_once(&onceToken, ^{
            
            sharedLocationManager = [[LocationManager alloc] init];
            
            [sharedLocationManager setupLocationManager];
        });
    }
    
    return  sharedLocationManager;
}

-(void)setupLocationManager
{
    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.distanceFilter = kCLDistanceFilterNone;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    self.locationManager.delegate = self;
    
    [self.locationManager stopUpdatingLocation];
}

-(BOOL)amIAuthorized
{
    if ([CLLocationManager authorizationStatus] == kCLAuthorizationStatusDenied ||
        [CLLocationManager authorizationStatus] == kCLAuthorizationStatusNotDetermined ||
        [CLLocationManager authorizationStatus] ==kCLAuthorizationStatusRestricted)
        return NO;
    else
        return YES;
    
}

-(void)requestLocation
{
    if (self.locationManager == nil)
        [self setupLocationManager];
    
    if (self.locationManager.delegate == nil)
        self.locationManager.delegate = self;

    [self.locationManager startUpdatingLocation];
}

-(void)requestAuthorization
{
    CLAuthorizationStatus authorizationStatus = [CLLocationManager authorizationStatus];
    
    if ((authorizationStatus != kCLAuthorizationStatusAuthorizedWhenInUse || authorizationStatus != kCLAuthorizationStatusAuthorizedAlways)
        && [self.locationManager respondsToSelector:@selector(requestWhenInUseAuthorization)])
        [self.locationManager requestWhenInUseAuthorization];
}

- (void) locationManager:(CLLocationManager *)manager didChangeAuthorizationStatus:(CLAuthorizationStatus)status
{
    
     [[NSNotificationCenter defaultCenter] postNotificationName:@"locationManagerDidChangeAuthorizationStatus" object:@(status)];
}

-(void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations
{
    [[NSNotificationCenter defaultCenter] postNotificationName:@"didFindLocation" object:self.locationManager.location];
    
    self.lastUserLocation = locations.lastObject;
    NSLog(@"###LAST USER LOCATION : %@", self.lastUserLocation);
    
    [self.locationManager stopUpdatingLocation];
    self.locationManager = nil;
}
@end
