//
//  LocationManager.h
//  WeatherIPed
//
//  Created by Vlad Minea on 19/05/16.
//  Copyright © 2016 Vlad Minea. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>

@interface LocationManager : NSObject <CLLocationManagerDelegate>

@property (nonatomic, strong) CLLocationManager *locationManager;
@property (nonatomic, strong) CLGeocoder *geocoder;
@property (nonatomic, strong) CLLocation *lastUserLocation;

@property (nonatomic) CLAuthorizationStatus lastStatus;

+(LocationManager *)sharedInstance;

-(BOOL)amIAuthorized;
-(void)requestLocation;
-(void)requestAuthorization;

@end

@compatibility_alias LM LocationManager;

#define _LM [LocationManager sharedInstance]