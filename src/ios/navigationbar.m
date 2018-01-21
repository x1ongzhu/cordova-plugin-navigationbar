/********* navigationbar.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface navigationbar : CDVPlugin<UINavigationBarDelegate> {
  // Member variables go here.
}

@property (nonatomic, strong) UINavigationBar *navBar;
@property (nonatomic, strong) NSString *titleText;
@property (nonatomic, strong) UINavigationItem *navigationItem;
@property (nonatomic) Boolean showBack;

- (void)create:(CDVInvokedUrlCommand*)command;
- (void)setBg:(CDVInvokedUrlCommand*)command;
- (void)setGradientBg:(CDVInvokedUrlCommand*)command;
- (void)setStyle:(CDVInvokedUrlCommand*)command;
- (void)setTitle:(CDVInvokedUrlCommand*)command;
- (void)coolMethod:(CDVInvokedUrlCommand*)command;
- (void)showBack:(CDVInvokedUrlCommand*)command;
- (void)hideBack:(CDVInvokedUrlCommand*)command;
@end

@implementation navigationbar

- (void)create:(CDVInvokedUrlCommand*)command{
    if (!_navBar) {
        _navBar = [[UINavigationBar alloc] initWithFrame:CGRectMake(0, 20, [UIScreen mainScreen].bounds.size.width, 44)];
        _navBar.delegate = self;
        [_navBar setTranslucent:NO];
        [_navBar setShadowImage:[[UIImage alloc] init]];
        _navigationItem = [[UINavigationItem alloc] initWithTitle:@""];
        NSArray *items = [[NSArray alloc] initWithObjects:_navigationItem,nil,nil];
        [_navBar setItems:items animated:NO];
        [self.webView.superview addSubview:_navBar];
        [self.webView setFrame:CGRectMake(0, 44 + 20, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height - 64)];
    }
}

- (void)setBg:(CDVInvokedUrlCommand*)command{
    if (_navBar) {
        [_navBar setBarTintColor:[self getColor:[command.arguments objectAtIndex:0]]];
    }
}

- (void)setGradientBg:(CDVInvokedUrlCommand*)command{
    if (_navBar) {
        UIColor *color1 = [self getColor:[command.arguments objectAtIndex:0]];
        UIColor *color2 = [self getColor:[command.arguments objectAtIndex:1]];
        
        CAGradientLayer *gradient = [CAGradientLayer layer];
        gradient.frame = CGRectMake(0, 0, [UIScreen mainScreen].bounds.size.width, 64);
        gradient.colors = @[(id)color1.CGColor, (id)color2.CGColor];
        gradient.startPoint = CGPointMake(0, 0.5);
        gradient.endPoint = CGPointMake(1, 0.5);
        
        [_navBar setBackgroundImage:[self imageFromLayer:gradient] forBarMetrics:UIBarMetricsDefault];
    }
}

- (void)setStyle:(CDVInvokedUrlCommand*)command{
    if (_navBar) {
        NSString *style = [command.arguments objectAtIndex:0];
        if ([@"light" isEqualToString:[style lowercaseString] ]) {
            [_navBar setTintColor:[UIColor whiteColor]];
            [_navBar setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]}];

        }else if ([@"dark" isEqualToString:[style lowercaseString] ]) {
            [_navBar setTintColor:[UIColor blackColor]];
            [_navBar setTitleTextAttributes:@{NSForegroundColorAttributeName:[UIColor whiteColor]}];
        }
    }
}

- (void)setTitle:(CDVInvokedUrlCommand*)command{
    if(_navBar){
        [_navigationItem setTitle: [command.arguments objectAtIndex:0]];
    }
}

- (void)showBack:(CDVInvokedUrlCommand*)command{
    if (_navBar) {
        NSString * bundlePath = [[ NSBundle mainBundle] pathForResource: @ "Navigationbar" ofType :@ "bundle"];
        NSBundle *resourceBundle = [NSBundle bundleWithPath:bundlePath];
        UIImage *img = [UIImage imageNamed:@"icon_back"
                                  inBundle:resourceBundle
             compatibleWithTraitCollection:nil];
        _navigationItem.leftBarButtonItem = [[UIBarButtonItem alloc] initWithImage:img
                                                                             style:UIBarButtonItemStylePlain
                                                                            target:self
                                                                            action:@selector(click:)];
    }
}

- (void)hideBack:(CDVInvokedUrlCommand*)command{
    if (_navBar) {
        _navigationItem.leftBarButtonItem = nil;
    }
}

-(void)click:(UIButton *)button{
    [self.webViewEngine evaluateJavaScript:@"history.go(1);" completionHandler:^(id res, NSError * error) {
        
    }];
}

- (void)coolMethod:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    NSString* echo = [command.arguments objectAtIndex:0];

    if (echo != nil && [echo length] > 0) {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
    } else {
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (UIImage *)imageFromLayer:(CALayer *)layer
{
    UIGraphicsBeginImageContext([layer frame].size);
    
    [layer renderInContext:UIGraphicsGetCurrentContext()];
    UIImage *outputImage = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    return outputImage;
}

- (UIBarPosition)positionForBar:(id <UIBarPositioning>)bar{
    return UIBarPositionTopAttached;
}

- (UIColor *)getColor:(NSString *)hexColor
{
    if ([hexColor hasPrefix:@"#"]) {
        hexColor = [hexColor substringFromIndex:1];
    }
    unsigned int red,green,blue;
    NSRange range;
    range.length = 2;
    range.location = 0;
    [[NSScanner scannerWithString:[hexColor substringWithRange:range]] scanHexInt:&red];
    range.location = 2;
    [[NSScanner scannerWithString:[hexColor substringWithRange:range]] scanHexInt:&green];
    range.location = 4;
    [[NSScanner scannerWithString:[hexColor substringWithRange:range]] scanHexInt:&blue];
    return [UIColor colorWithRed:(float)(red/255.0f) green:(float)(green / 255.0f) blue:(float)(blue / 255.0f) alpha:1.0f];
}

@end
