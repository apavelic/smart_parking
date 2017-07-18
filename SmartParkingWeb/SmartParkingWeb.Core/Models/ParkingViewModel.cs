using Newtonsoft.Json;
using SmartParkingWeb.Core.Enumerations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SmartParkingWeb.Core.Models
{
    public class ParkingViewModel
    {
        [JsonProperty("id")]
        public int Id { get; set; }
        [JsonProperty("state")]
        public ParkingStateEnum State { get; set; }
        [JsonProperty("location")]
        public String Location { get; set; }
    }
}
